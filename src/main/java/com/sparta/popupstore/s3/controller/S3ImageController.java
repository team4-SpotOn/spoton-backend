package com.sparta.popupstore.s3.controller;

import com.sparta.popupstore.s3.dto.request.ImageRequestDto;
import com.sparta.popupstore.s3.dto.response.S3UrlResponseDto;
import com.sparta.popupstore.s3.enums.Directory;
import com.sparta.popupstore.s3.service.S3ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "s3 버킷", description = "s3에 이미지를 저장하기 위한 preSignedUrl 발급 api")
@RestController
@RequiredArgsConstructor
public class S3ImageController {

    private final S3ImageService imageService;

    @Operation(summary = "리뷰 & 프로모션 이벤트 이미지 preSignedUrl 발급")
    @Parameter(name = "fileName", description = "파일명")
    @GetMapping("/{directory}/images/preassigned")
    public ResponseEntity<S3UrlResponseDto> getReviewAndPromotionEventImagePreSignedUrl(
            @PathVariable String directory,
            @RequestBody @Valid ImageRequestDto imageRequestDto
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.getPreSignedUrl(directory, imageRequestDto.getFileName()));
    }

    @Operation(summary = "팝업스토어 이미지 preSignedUrl 발급")
    @Parameter(name = "fileName", description = "파일명")
    @GetMapping("/popup-stores/images/preassigned")
    public ResponseEntity<List<S3UrlResponseDto>> getPopupStoreImagePreSignedUrl(
            @RequestBody @NotEmpty(message = "이미지를 하나이상 올려주세요") List<ImageRequestDto> imageRequestDtoList
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.getPreSignedUrls(Directory.POPUP_STORES.getDomain(), imageRequestDtoList));
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
