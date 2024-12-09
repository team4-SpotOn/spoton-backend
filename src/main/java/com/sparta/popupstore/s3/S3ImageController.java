package com.sparta.popupstore.s3;

import com.sparta.popupstore.s3.dto.request.ReviewImageRequestDto;
import com.sparta.popupstore.s3.dto.response.S3UrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class S3ImageController {

    private final S3ImageService imageService;

    @GetMapping("/reviews/image/preassigned")
    public ResponseEntity<S3UrlResponseDto> getPreSignedUrl(
            @RequestBody ReviewImageRequestDto reviewImageRequestDto
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.getPreSignedUrl("review", reviewImageRequestDto.getFileName()));
    }
}
