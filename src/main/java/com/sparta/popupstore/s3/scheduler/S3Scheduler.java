package com.sparta.popupstore.s3.scheduler;

import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreImage;
import com.sparta.popupstore.domain.popupstore.bundle.repository.PopupStoreImageRepository;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.review.repository.ReviewRepository;
import com.sparta.popupstore.s3.enums.Directory;
import com.sparta.popupstore.s3.service.S3ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Scheduler {
    private final ReviewRepository reviewRepository;
    private final PromotionEventRepository promotionEventRepository;
    private final PopupStoreImageRepository popupStoreImageRepository;
    private final S3ImageService s3ImageService;
    @Value("${cloud.aws.url}")
    private String baseUrl;

    @Scheduled(fixedRate = 500000000)
    @Scheduled(cron = "0 0 1 * * *")
    public void s3ReviewImageDelete(){
        log.info("s3 Review 쓰이지 않는 이미지 삭제 스케줄러 실행");
        List<String> reviewImages = reviewRepository.findAll()
                .stream()
                .map(Review::getImageUrl)
                .toList();
        this.s3ImageDeleteByPrefix(reviewImages, Directory.REVIEWS);
        log.info("s3 Review 쓰이지 않는 이미지 삭제 스케줄러 종료");
    }

    @Scheduled(fixedRate = 500000000)
    @Scheduled(cron = "0 30 2 * * *")
    public void s3PromotionEventImageDelete(){
        log.info("s3 PromotionEvent 쓰이지 않는 이미지 삭제 스케줄러 실행");
        List<String> promotionEventImages = promotionEventRepository.findAll()
                .stream()
                .map(PromotionEvent::getImageUrl)
                .toList();
        this.s3ImageDeleteByPrefix(promotionEventImages, Directory.PROMOTION_EVENTS);
        log.info("s3 PromotionEvent 쓰이지 않는 이미지 삭제 스케줄러 종료");
    }

    @Scheduled(fixedRate = 500000000)
    @Scheduled(cron = "0 0 4 * * *")
    public void s3PopupStoreImageDelete(){
        log.info("s3 PopupStore 쓰이지 않는 이미지 삭제 스케줄러 실행");
        List<String> promotionEventImages = popupStoreImageRepository.findAll()
                .stream()
                .map(PopupStoreImage::getImageUrl)
                .toList();
        this.s3ImageDeleteByPrefix(promotionEventImages, Directory.POPUP_STORES);
        log.info("s3 PopupStore 쓰이지 않는 이미지 삭제 스케줄러 종료");
    }

    private void s3ImageDeleteByPrefix(List<String> images, Directory prefix){
        List<String> deleteImages = s3ImageService.getImages(prefix)
                .stream()
                .filter(imageUrl -> !images.contains(baseUrl+imageUrl))
                .toList();
        if(!deleteImages.isEmpty()){
            s3ImageService.deleteImages(deleteImages);
        }
    }
}
