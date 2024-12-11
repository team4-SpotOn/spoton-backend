package com.sparta.popupstore.domain.promotionevent.scheduler;

import com.sparta.popupstore.domain.common.util.ValidUtil;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.promotionevent.repository.CouponRepository;
import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
import com.sparta.popupstore.s3.S3ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    @Scheduled(cron = "00 1 0 * * *") // 매일 00시 1분
    public void hardDeletePromotionEvent() {
        log.info("hardDeletePromotionEvent 스케줄러");
        promotionEventRepository.deletePromotionEventByDeletedAtAfterSixMonths();
    }

    @Transactional
    @Scheduled(fixedRate = 300000) // 테스트 용
    @Scheduled(cron = "30 1 0 * * *") // 매일 00시 1분 30초
    public void softDeleteCoupon() {
        log.info("softDeleteCoupon 스케줄러");
        couponRepository.softDeleteCouponByExpiration();
    }

    @Transactional
    @Scheduled(fixedRate = 3600001) // 1시간마다 실행
    public void softDeletePromotionEvent(){
        log.info("이벤트 softDelete 스케줄러");
        List<PromotionEvent> eventList = promotionEventRepository.findAllByEndDateTimeBeforeAndDeletedAtIsNull(LocalDateTime.now());
        for(PromotionEvent event : eventList){
            if(ValidUtil.isValidNullAndEmpty(event.getImageUrl())){
                s3ImageService.deleteImage(event.getImageUrl());
            }
        }
        promotionEventRepository.softDeletePromotionEventByTerminated();
    }
}
