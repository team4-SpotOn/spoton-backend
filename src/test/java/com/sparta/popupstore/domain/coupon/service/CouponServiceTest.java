package com.sparta.popupstore.domain.coupon.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.coupon.repository.CouponRepository;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
import com.sparta.popupstore.domain.promotionevent.service.PromotionEventService;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import net.bytebuddy.utility.dispatcher.JavaDispatcher.Container;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers // Testcontainers 활성화
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CouponServiceTest {

    private static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName(System.getenv("DB_NAME"))  // GitHub Secrets에서 가져온 DB 이름
        .withUsername(System.getenv("DB_USER"))  // GitHub Secrets에서 가져온 사용자명
        .withPassword(System.getenv("DB_PASSWORD"));  // GitHub Secrets에서 가져온 비밀번호

    @Autowired
    private PromotionEventService promotionEventService;

    @Autowired
    private PromotionEventRepository promotionEventRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserRepository userRepository;

    PromotionEvent promotionEvent;

    @BeforeEach
    public void setup() {
        // 데이터베이스 연결 및 초기화 작업
        System.setProperty("DB_HOST", mysqlContainer.getHost());
        System.setProperty("DB_PORT", String.valueOf(mysqlContainer.getMappedPort(3306)));
        System.setProperty("DB_NAME", mysqlContainer.getDatabaseName());
        System.setProperty("DB_USER", mysqlContainer.getUsername());
        System.setProperty("DB_PASSWORD", mysqlContainer.getPassword());

        // 이벤트 초기화 (couponGetCount 초기값을 0으로 설정)

        promotionEvent = PromotionEvent.builder()
            .couponGetCount(0)
            .totalCount(5)
            .discountPercentage(10)
            .couponExpirationPeriod(30)
            .title("선착순테스트")
            .description("설명설명")
            .startDateTime(LocalDateTime.now())
            .endDateTime(LocalDateTime.now().plusDays(30))
            .build();
        promotionEventRepository.save(promotionEvent);


        // 사용자 10명 생성
        for (int i = 1; i <= 10; i++) {
            User user = User.builder()
                .name("user" + i)
                .point(0)
                .email("user" + i + "@example.com")
                .build();
            userRepository.save(user);
        }
    }

//    @Test
//    @Transactional
//    @DisplayName("이벤트 쿠폰 선착순 5명 - 10명이 경쟁 (mock 유저 임의 생성)")
//    public void testCouponConcurrency10() throws InterruptedException {
//        final int threads = 100;
//        final int usersCount = 10;
//        final int count = 10;
//
//
//        Long promotionEventId = promotionEvent.getId();
////        Long promotionEventId = 2L;
//        ExecutorService executorService = Executors.newFixedThreadPool(threads);
////        when(user.getId()).thenReturn(1L);
//        // 객체 여러명 생성 반복문
//        List<User> users = new ArrayList<>();
//        for (int i = 0; i < usersCount; i++) {
//            User user = mock(User.class);
//            when(user.getId()).thenReturn((long)(i + 1));
//            users.add(user);
//        }
//
//        for (User user : users) {
//            executorService.submit(() -> {
//                try {
//                    couponService.couponApplyAndIssuance(user, promotionEventId);
//                } catch (CustomApiException e) {
//                    System.out.println("쿠폰 지급 한도 초과");
//                }
//            });
//        }
//
//        executorService.shutdown();
//        executorService.awaitTermination(1, TimeUnit.MINUTES);
//
//        PromotionEvent promotionEvent = promotionEventRepository.findById(promotionEventId)
//            .orElseThrow(() -> new CustomApiException(ErrorCode.PROMOTION_EVENT_NOT_FOUND));
//        assertTrue(promotionEvent.getCouponGetCount() <= count);
//
//        long couponCount = couponRepository.countByPromotionEventId(promotionEventId);
//        assertEquals(count, couponCount);
//    }

    @Test
    @DisplayName("이벤트 쿠폰 선착순 5명 - 10명이 경쟁 (user DB사용)")
    public void testCouponConcurrency100() throws InterruptedException {
        final int threads = 100;
        final int count = 5;

        Long promotionEventId = promotionEvent.getId();
//        System.out.println("프로모션 이벤트 : "+ promotionEventId);
//        Long promotionEventId = 13L;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        List<User> users = userRepository.findAll();
        for (User user : users) {
            executorService.submit(() -> {
                try {
                    System.out.println("선착순 쿠폰 실행 전");
                    promotionEventService.couponApplyAndIssuance(user, promotionEventId);
                    System.out.println("선착순 쿠폰 실행 후");
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
