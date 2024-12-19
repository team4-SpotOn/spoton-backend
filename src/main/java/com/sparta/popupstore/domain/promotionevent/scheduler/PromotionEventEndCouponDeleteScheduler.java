package com.sparta.popupstore.domain.promotionevent.scheduler;

import com.sparta.popupstore.domain.common.util.ValidUtil;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.promotionevent.repository.CouponRepository;
import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
import com.sparta.popupstore.s3.service.S3ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
        List<PromotionEvent> eventList = promotionEventRepository.findAllByEndDateTimeAfterSixMonths();
        for(PromotionEvent event : eventList) {
            if(ValidUtil.isValidNullAndEmpty(event.getImageUrl())) {
                s3ImageService.deleteImage(event.getImageUrl());
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
