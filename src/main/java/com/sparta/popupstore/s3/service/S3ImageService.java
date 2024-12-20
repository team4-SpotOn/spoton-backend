package com.sparta.popupstore.s3.service;


import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.s3.dto.request.ImageRequestDto;
import com.sparta.popupstore.s3.dto.response.S3UrlResponseDto;
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
    private final AmazonS3 amazonS3;
    private static final int SIGN_LIFE_TIME = 1000 * 60 * 2;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.url}")
    private String baseUrl;


    public S3UrlResponseDto getPreSignedUrl(Directory prefix, String fileName){
        this.validFileName(fileName);
        String createFileName = String.format("%s/%s", prefix.getDomain(), this.createUuid() + fileName);
        String preSignedUrl = generateResignedUrlRequest(createFileName);
        return S3UrlResponseDto.builder()
                .preSignedUrl(preSignedUrl)
                .imageUrl(baseUrl +createFileName)
                .build();
    }

    public List<S3UrlResponseDto> getPreSignedUrls(Directory prefix, List<ImageRequestDto> imageRequestDtoList) {
        return imageRequestDtoList
                .stream()
                .map(image ->
                        this.getPreSignedUrl(prefix, image.getFileName())
                )
                .toList();
    }

    public void deleteImage(String fileName) {
        if(!fileName.contains(baseUrl)){
            throw new CustomApiException(ErrorCode.NOT_CORRECT_URL_FORMAT);
        }
        String key = fileName.substring(baseUrl.length());
        try{
            amazonS3.deleteObject(bucket, key);
        }
        catch (AmazonS3Exception e){
            throw new CustomApiException(ErrorCode.FAIL_DELETE_IMAGE_FILE);
        }
    }

    private String generateResignedUrlRequest(String fileName) {
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
                    .withMethod(HttpMethod.PUT)
                    .withExpiration(this.getPreSignedUrlExpiration());
            generatePresignedUrlRequest.addRequestParameter(
                    Headers.S3_CANNED_ACL,
                    CannedAccessControlList.PublicRead.toString()
            );
            return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    private void validFileName(String fileName) {
        if(!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".jpeg")) {
            throw new CustomApiException(ErrorCode.NOT_CORRECT_FORMAT);
        }
    }

    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += SIGN_LIFE_TIME;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    private String createUuid(){
        return UUID.randomUUID().toString();
    }
}
