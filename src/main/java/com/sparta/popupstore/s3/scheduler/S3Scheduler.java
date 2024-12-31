//package com.sparta.popupstore.s3.scheduler;
//
//import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreImage;
//import com.sparta.popupstore.domain.popupstore.bundle.repository.PopupStoreImageRepository;
//import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
//import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
//import com.sparta.popupstore.domain.review.entity.Review;
//import com.sparta.popupstore.domain.review.repository.ReviewRepository;
//import com.sparta.popupstore.s3.enums.Directory;
//import com.sparta.popupstore.s3.service.S3ImageService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class S3Scheduler {
//    private final ReviewRepository reviewRepository;
//    private final PromotionEventRepository promotionEventRepository;
//    private final PopupStoreImageRepository popupStoreImageRepository;
//    private final S3ImageService s3ImageService;
//    private static final boolean RECURSIVE = true;
//
//    @Scheduled(fixedRate = 500000000)
//    @Scheduled(cron = "0 0 1 * * *", zone = "Asia/Seoul")
//    public void s3ReviewImageDelete(){
//        log.info("s3 Review 쓰이지 않는 이미지 삭제 스케줄러 실행");
//        List<String> reviewImages = reviewRepository.findAll()
//                .stream()
//                .map(Review::getImageUrl)
//                .toList();
//        s3ImageService.s3ImageDeleteByPrefix(reviewImages, Directory.REVIEWS, RECURSIVE);
//        log.info("s3 Review 쓰이지 않는 이미지 삭제 스케줄러 종료");
//    }
//
//    @Scheduled(fixedRate = 500000000)
//    @Scheduled(cron = "0 30 2 * * *", zone = "Asia/Seoul")
//    public void s3PromotionEventImageDelete(){
//        log.info("s3 PromotionEvent 쓰이지 않는 이미지 삭제 스케줄러 실행");
//        List<String> promotionEventImages = promotionEventRepository.findAll()
//                .stream()
//                .map(PromotionEvent::getImageUrl)
//                .toList();
//        s3ImageService.s3ImageDeleteByPrefix(promotionEventImages, Directory.PROMOTION_EVENTS, RECURSIVE);
//        log.info("s3 PromotionEvent 쓰이지 않는 이미지 삭제 스케줄러 종료");
//    }
//
//    @Scheduled(fixedRate = 500000000)
//    @Scheduled(cron = "0 0 4 * * *", zone = "Asia/Seoul")
//    public void s3PopupStoreImageDelete(){
//        log.info("s3 PopupStore 쓰이지 않는 이미지 삭제 스케줄러 실행");
//        List<String> popupStoreImages = popupStoreImageRepository.findAll()
//                .stream()
//                .map(PopupStoreImage::getImageUrl)
//                .toList();
//        s3ImageService.s3ImageDeleteByPrefix(popupStoreImages, Directory.POPUP_STORES, RECURSIVE);
//        log.info("s3 PopupStore 쓰이지 않는 이미지 삭제 스케줄러 종료");
//    }
//}
