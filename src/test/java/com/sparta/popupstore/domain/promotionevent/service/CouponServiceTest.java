package com.sparta.popupstore.domain.promotionevent.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.promotionevent.repository.CouponRepository;
import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
import com.sparta.popupstore.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private PromotionEventService promotionEventService;

    @Autowired
    private PromotionEventRepository promotionEventRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponService couponService;

    private PromotionEvent promotionEvent;

    @MockBean
    private User user;  // User 객체를 MockBean으로 설정


//    @BeforeEach
//    public void setup() {
//        // 이벤트 초기화 (couponGetCount 초기값을 0으로 설정)
//
//        promotionEvent = PromotionEvent.builder()
//            .couponGetCount(0)
//            .totalCount(10)
//            .discountPercentage(10)
//            .couponExpirationPeriod(30)
//            .title("선착순테스트")
//            .description("설명설명")
//            .endDateTime(LocalDateTime.now())
//            .build();
//        promotionEventRepository.save(promotionEvent);
//    }

    @Test
    @DisplayName("이벤트 쿠폰 선착순 10명 - 100명 동시성 제어 테스트")
    public void testCouponConcurrency() throws InterruptedException {
        Long promotionEventId = 2L;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
//        when(user.getId()).thenReturn(1L);
        // 객체 여러명 생성 반복문
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = mock(User.class);
            when(user.getId()).thenReturn((long)(i + 1));
            users.add(user);
        }

        for (int i = 1; i <= 100; i++) {
            final User user = users.get(i % 10);
            executorService.submit(() -> {
                try {
                    promotionEventService.couponApplyAndIssuance(promotionEventId, user);
                } catch (CustomApiException e) {
                    System.out.println("쿠폰 지급 한도 초과");
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        PromotionEvent promotionEvent = promotionEventRepository.findById(promotionEventId)
            .orElseThrow(() -> new CustomApiException(ErrorCode.PROMOTION_EVENT_NOT_FOUND));
        assertTrue(promotionEvent.getCouponGetCount() <= 10);

        long couponCount = couponRepository.countByPromotionEventId(promotionEventId);
        assertEquals(10, couponCount);
    }
}