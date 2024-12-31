package com.sparta.popupstore.s3.service;


import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringUtils;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.s3.dto.request.S3ImageListRequestDto;
import com.sparta.popupstore.s3.dto.request.S3ImageRequestDto;
import com.sparta.popupstore.s3.dto.response.S3ImageResponseDto;
import com.sparta.popupstore.s3.enums.Directory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.model.MultiObjectDeleteException.DeleteError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ImageService {
    private static final int SIGN_LIFE_TIME = 1000 * 60 * 2;
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.url}")
    private String baseUrl;


    public S3ImageResponseDto getPreSignedUrl(Directory prefix, S3ImageRequestDto requestDto) {
        String fileName = requestDto.getFileName();
        if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".jpeg")) {
            throw new CustomApiException(ErrorCode.NOT_CORRECT_FORMAT);
        }
        String uuid = UUID.randomUUID().toString();
        String createFileName = prefix.getPrefix() + "/" + uuid + fileName;
        String preSignedUrl = generatePreSignedUrl(createFileName);
        return S3ImageResponseDto.builder()
                .preSignedUrl(preSignedUrl)
                .imageUrl(baseUrl + createFileName)
                .build();
    }

    public List<S3ImageResponseDto> getPreSignedUrls(Directory prefix, S3ImageListRequestDto requestDto) {
        return requestDto.getImageRequestDtoList().stream()
                .map(imageRequestDto -> this.getPreSignedUrl(prefix, imageRequestDto))
                .toList();
    }

    public List<String> getImages(Directory prefix) {
        List<String> images = new ArrayList<>();
        String continuationToken = null;
        do {
            ListObjectsV2Request request = new ListObjectsV2Request()
                    .withBucketName(bucket)
                    .withPrefix(prefix.getPrefix())
                    .withContinuationToken(continuationToken);
            ListObjectsV2Result result = amazonS3.listObjectsV2(request);
            result.getObjectSummaries().forEach(s3Object -> images.add(s3Object.getKey()));
            // 다음 페이지를 위한 ContinuationToken 설정
            continuationToken = result.getNextContinuationToken();
        } while (continuationToken != null); // 다음 페이지가 없으면 종료
        return images;
    }

    // 여기 이 메스드 어떻게 할까요 ??
    public void deleteImage(String fileName) {
        if (StringUtils.isNullOrEmpty(fileName)) {
            log.info("null or empty file name");
            return;
        }
        if (!fileName.contains(baseUrl)) {
            throw new CustomApiException(ErrorCode.NOT_CORRECT_URL_FORMAT);
        }
        String key = fileName.substring(baseUrl.length());
        try {
            amazonS3.deleteObject(bucket, key);
        } catch (AmazonS3Exception e) {
            throw new CustomApiException(ErrorCode.FAIL_DELETE_IMAGE_FILE);
        }
    }

    public void deleteImages(List<String> images) {
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucket)
                .withKeys(images.toArray(new String[0]));
        try {
            amazonS3.deleteObjects(deleteObjectsRequest);
        } catch (MultiObjectDeleteException e) {
            List<DeleteError> errors = e.getErrors();
            errors.forEach(error -> log.warn("스케줄러 S3 이미지 삭제 MultiObjectDeleteException : {} {}",error.getKey(), error.getMessage()));
            this.retryImagesDelete(errors);
        }
        catch (AmazonS3Exception e) {
            log.error("스케줄러 s3 이미지 삭제 AmazonS3Exception : {}", e.getMessage());
        }
    }

    private void retryImagesDelete(List<DeleteError> errorImageList){
        errorImageList.stream().map(DeleteError::getKey).forEach(key -> {
           try {
               amazonS3.deleteObject(bucket, key);
           }catch (MultiObjectDeleteException e){
               log.error("스케줄러 s3 error 이미지 삭제 재시도 MultiObjectDeleteException : {}. 에러 메세지 : {}", key, e.getMessage());
           }
           catch (AmazonS3Exception e) {
               log.error("스케줄러 s3 이미지 삭제 재시도 AmazonS3Exception : {}. 에러 메세지 : {}",key, e.getMessage());
           }
        });
    }

    private String generatePreSignedUrl(String createFileName) {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + SIGN_LIFE_TIME);
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, createFileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiration);

        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString()
        );
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }
}
