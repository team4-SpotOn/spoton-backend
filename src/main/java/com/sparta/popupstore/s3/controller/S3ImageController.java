package com.sparta.popupstore.s3.controller;

import com.sparta.popupstore.s3.service.S3ImageService;
import com.sparta.popupstore.s3.dto.request.ImageRequestDto;
import com.sparta.popupstore.s3.dto.response.S3UrlResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "s3 버킷", description = "s3에 이미지를 저장하기 위한 preSignedUrl 발급 api")
@RestController
@RequiredArgsConstructor
public class S3ImageController {

    private final S3ImageService imageService;

    @Operation(summary = "리뷰 이미지 preSignedUrl 발급")
    @Parameter(name = "fileName", description = "파일명")
    @GetMapping("/reviews/images/preassigned")
    public ResponseEntity<S3UrlResponseDto> getReviewImagePreSignedUrl(
            @RequestBody ImageRequestDto imageRequestDto
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.getPreSignedUrl("review", imageRequestDto.getFileName()));
    }

    @Operation(summary = "프로모션 이벤트 이미지 preSignedUrl 발급")
    @Parameter(name = "fileName", description = "파일명")
    @GetMapping("/promotion-events/images/preassigned")
    public ResponseEntity<S3UrlResponseDto> getPromotionEventImagePreSignedUrl(
            @RequestBody ImageRequestDto imageRequestDto
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.getPreSignedUrl("promotionevent", imageRequestDto.getFileName()));
    }

    @Operation(summary = "프로모션 이벤트 이미지 preSignedUrl 발급")
    @Parameter(name = "fileName", description = "파일명")
    @Parameter(name = "sort", description = "이미지 순서")
    @GetMapping("/popup-stores/images/preassigned")
    public ResponseEntity<List<S3UrlResponseDto>> getPopupStoreImagePreSignedUrl(
            @RequestBody @NotEmpty(message = "이미지를 하나이상 올려주세요") List<ImageRequestDto> imageRequestDtoList
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.getPreSignedUrls("popupstore",imageRequestDtoList));
    }

    @Operation(summary = "s3에 저장된 이미지 삭제")
    @Parameter(name = "fileName", description = "파일명")
    @DeleteMapping("/images")
    public ResponseEntity<Void> deleteImage(
            @RequestBody ImageRequestDto imageRequestDto
    ){
        imageService.deleteImage(imageRequestDto.getFileName());
        return ResponseEntity.noContent().build();
    }
}
