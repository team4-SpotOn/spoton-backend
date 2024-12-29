package com.sparta.popupstore;

import com.sparta.popupstore.domain.coupon.repository.CouponRepository;
import com.sparta.popupstore.domain.coupon.service.CouponService;
import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
import com.sparta.popupstore.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class PopupStoreApplicationTests {

	@Autowired
	private CouponService couponService;  // 실제 빈을 주입받음

	@Autowired
	private PromotionEventRepository promotionEventRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
		assert(couponService != null);
		assert(promotionEventRepository != null);
		assert(couponRepository != null);
		assert(userRepository != null);
	}
}