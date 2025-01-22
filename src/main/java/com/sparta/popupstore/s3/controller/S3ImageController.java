//package com.sparta.popupstore.s3.controller;
//
//import com.sparta.popupstore.s3.dto.request.S3ImageListRequestDto;
//import com.sparta.popupstore.s3.dto.request.S3ImageRequestDto;
//import com.sparta.popupstore.s3.dto.response.S3ImageResponseDto;
//import com.sparta.popupstore.s3.enums.Directory;
//import com.sparta.popupstore.s3.service.S3ImageService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Tag(name = "s3 버킷", description = "s3에 이미지를 저장하기 위한 preSignedUrl 발급 api")
//@RestController
//@RequiredArgsConstructor
//public class S3ImageController {
//
////    private final S3ImageService imageService;
//
//    @Operation(summary = "리뷰 & 프로모션 이벤트 이미지 preSignedUrl 발급")
//    @Parameter(name = "directory", description = "이미지 저장 경로")
//    @Parameter(name = "fileName", description = "파일명")
//    @GetMapping("/{directory}/images/preassigned")
//    public ResponseEntity<S3ImageResponseDto> getReviewAndPromotionEventImagePreSignedUrl(
//            @PathVariable Directory directory,
//            @RequestBody @Valid S3ImageRequestDto requestDto
//    ) {
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(imageService.getPreSignedUrl(directory, requestDto));
//    }
//
//    @Operation(summary = "팝업스토어 이미지 preSignedUrl 발급")
//    @Parameter(name = "fileName", description = "파일명")
//    @GetMapping("/popup-stores/images/preassigned")
//    public ResponseEntity<List<S3ImageResponseDto>> getPopupStoreImagePreSignedUrl(
//            @RequestBody @Valid S3ImageListRequestDto requestDto
//    ) {
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(imageService.getPreSignedUrls(Directory.POPUP_STORES, requestDto));
//    }
//
//    @Operation(summary = "s3에 저장된 이미지 삭제")
//    @Parameter(name = "fileName", description = "파일명")
//    @DeleteMapping("/images")
//    public ResponseEntity<Void> deleteImage(
//            @RequestBody S3ImageRequestDto imageRequestDto
//    ) {
//        imageService.deleteImages(List.of(imageRequestDto.getFileName()), false);
//        return ResponseEntity
//                .status(HttpStatus.NO_CONTENT)
//                .build();
//    }
//}
