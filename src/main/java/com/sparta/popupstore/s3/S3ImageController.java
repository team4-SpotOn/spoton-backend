package com.sparta.popupstore.s3;

import com.sparta.popupstore.s3.dto.request.ImageRequestDto;
import com.sparta.popupstore.s3.dto.response.S3UrlResponseDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class S3ImageController {

    private final S3ImageService imageService;

    @GetMapping("/reviews/image/preassigned")
    public ResponseEntity<S3UrlResponseDto> getReviewImagePreSignedUrl(
            @RequestBody ImageRequestDto imageRequestDto
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.getPreSignedUrl("review", imageRequestDto.getFileName()));
    }

    @GetMapping("/promotion-events/image/preassigned")
    public ResponseEntity<S3UrlResponseDto> getPromotionEventImagePreSignedUrl(
            @RequestBody ImageRequestDto imageRequestDto
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.getPreSignedUrl("promotionevent", imageRequestDto.getFileName()));
    }

    @GetMapping("/popup-stores/image/preassigned")
    public ResponseEntity<List<S3UrlResponseDto>> getPopupStoreImagePreSignedUrl(
            @RequestBody @NotEmpty(message = "이미지를 하나이상 올려주세요") List<ImageRequestDto> imageRequestDtoList
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.getPreSignedUrls("popupstore",imageRequestDtoList));
    }
}
