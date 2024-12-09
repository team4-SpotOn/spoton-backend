package com.sparta.popupstore.s3;


import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.s3.dto.response.S3UrlResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ImageService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.url}")
    private String url;

    public S3UrlResponseDto getPreSignedUrl(String prefix, String fileName){
        this.validFileName(fileName);
        fileName = this.createPath(prefix, fileName);
        String preSignedUrl = generateResignedUrlRequest(fileName);
        return S3UrlResponseDto.builder()
                .preSignedUrl(preSignedUrl)
                .imageUrl(url+fileName)
                .build();
    }

    private String generateResignedUrlRequest(String fileName) {
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
                    .withMethod(HttpMethod.PUT)
                    .withExpiration(this.getPreSignedUrlExpiration());
            generatePresignedUrlRequest.addRequestParameter(
                    Headers.S3_CANNED_ACL,
                    CannedAccessControlList.PublicRead.toString()
            );
            URL signedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
            return  signedUrl.toString();
    }

    private void validFileName(String fileName) {
        int index = fileName.lastIndexOf(".");
        if(!fileName.substring(index).equals(".jpg") && !fileName.substring(index).equals(".png")){
            throw new CustomApiException(ErrorCode.NOT_CORRECT_FORMAT);
        }
    }

    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    private String createUuid(){
        return UUID.randomUUID().toString();
    }

    private String createPath(String prefix, String fileName){
        String uuid = this.createUuid();
        return String.format("%s/%s", prefix, uuid + fileName);
    }
}
