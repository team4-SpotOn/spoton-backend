package com.sparta.popupstore.s3.service;


import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
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
        if(!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".jpeg")) {
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

    public void deleteImage(String fileName) {
        if(StringUtils.isNullOrEmpty(fileName)) {
            log.info("null or empty file name");
            return;
        }

        if(!fileName.contains(baseUrl)) {
            throw new CustomApiException(ErrorCode.NOT_CORRECT_URL_FORMAT);
        }
        String key = fileName.substring(baseUrl.length());
        try {
            amazonS3.deleteObject(bucket, key);
        } catch(AmazonS3Exception e) {
            throw new CustomApiException(ErrorCode.FAIL_DELETE_IMAGE_FILE);
        }
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
