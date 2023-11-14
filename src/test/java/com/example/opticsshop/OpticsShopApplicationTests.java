package com.example.opticsshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {BasketServiceTest.class, CustomUserDetailsServiceTest.class, ProductServiceTest.class, UserServiceTest.class} )
class OpticsShopApplicationTests {

	@Test
	void contextLoads() {
	}

}
