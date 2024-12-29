package com.sparta.popupstore.domain.coupon.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.coupon.repository.CouponRepository;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CouponServiceTest {

    @Mock
    private CouponService couponService;

    @Mock
    private PromotionEventRepository promotionEventRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private UserRepository userRepository;

    PromotionEvent promotionEvent;

    @BeforeEach
    public void setup() {
        // 이벤트 초기화 (couponGetCount 초기값을 0으로 설정)

        promotionEvent = PromotionEvent.builder()
            .couponGetCount(0)
            .totalCount(10)
            .discountPercentage(10)
            .couponExpirationPeriod(30)
            .title("선착순테스트")
            .description("설명설명")
            .endDateTime(LocalDateTime.now().plusDays(7))
            .build();
        promotionEventRepository.save(promotionEvent);
    }

    @Test
    @DisplayName("이벤트 쿠폰 선착순 5명 - 10명이 경쟁 (user DB 10명용)")
    public void testCouponConcurrency10() throws InterruptedException {
        final int threads = 100;
        final int usersCount = 10;
        final int count = 5;

        Long promotionEventId = 2L;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
//        when(user.getId()).thenReturn(1L);
        // 객체 여러명 생성 반복문
        List<User> users = new ArrayList<>();
        for (int i = 0; i < usersCount; i++) {
            User user = mock(User.class);
            when(user.getId()).thenReturn((long)(i + 1));
            users.add(user);
        }

        for (User user : users) {
            executorService.submit(() -> {
                try {
                    couponService.couponApplyAndIssuance(user, promotionEventId);
                } catch (CustomApiException e) {
                    System.out.println("쿠폰 지급 한도 초과");
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        PromotionEvent promotionEvent = promotionEventRepository.findById(promotionEventId)
            .orElseThrow(() -> new CustomApiException(ErrorCode.PROMOTION_EVENT_NOT_FOUND));
        assertTrue(promotionEvent.getCouponGetCount() <= count);

        long couponCount = couponRepository.countByPromotionEventId(promotionEventId);
        assertEquals(count, couponCount);
    }

    @Test
    @DisplayName("이벤트 쿠폰 선착순 10명 - 100명이 경쟁 (user DB 100명용)")
    public void testCouponConcurrency100() throws InterruptedException {
        final int threads = 100;
        final int count = 10;

        Long promotionEventId = 2L;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        List<User> users = userRepository.findAll();
        for (User user : users) {
            executorService.submit(() -> {
                try {
                    couponService.couponApplyAndIssuance(user, promotionEventId);
                } catch (CustomApiException e) {
                    System.out.println("쿠폰 지급 한도 초과");
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        PromotionEvent promotionEvent = promotionEventRepository.findById(promotionEventId)
            .orElseThrow(() -> new CustomApiException(ErrorCode.PROMOTION_EVENT_NOT_FOUND));
        assertTrue(promotionEvent.getCouponGetCount() <= count);

        long couponCount = couponRepository.countByPromotionEventId(promotionEventId);
        assertEquals(count, couponCount);
    }
}
