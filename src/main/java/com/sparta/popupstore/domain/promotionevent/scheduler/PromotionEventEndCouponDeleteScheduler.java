package com.sparta.popupstore.domain.promotionevent.scheduler;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.coupon.repository.CouponRepository;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
import com.sparta.popupstore.s3.service.S3ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PromotionEventEndCouponDeleteScheduler {
    private final PromotionEventRepository promotionEventRepository;
    private final CouponRepository couponRepository;
    private final S3ImageService s3ImageService;

    @Transactional
    @Scheduled(fixedRate = 300000) // 테스트 용
    @Scheduled(cron = "0 0 0 * * *") // 매일 00시 00분
    public void hardDeletePromotionEvent() {
        log.info("hardDeletePromotionEvent 스케줄러");

        LocalDateTime sixMonthBefore = LocalDateTime.now().minusMonths(6);
        List<PromotionEvent> eventList = promotionEventRepository.findAllByEndDateTimeBefore(sixMonthBefore);
        for(PromotionEvent event : eventList) {
            try {
                s3ImageService.deleteImage(event.getImageUrl());
            } catch(CustomApiException e) {
                log.error("msg : {}, status : {}", e.getMessage(), e.getErrorCode());
            }
        }
        promotionEventRepository.deleteAllInBatch(eventList);
    }

    @Transactional
    @Scheduled(fixedRate = 300000) // 테스트 용
    @Scheduled(cron = "0 0 0 * * *") // 매일 00시 00분
    public void softDeleteCoupon() {
        log.info("softDeleteCoupon 스케줄러");
        couponRepository.softDeleteCouponByExpiration();
    }
}
