package com.example.opticsshop;


import com.example.opticsshop.DAO.models.Basket;
import com.example.opticsshop.DAO.models.Product;
import com.example.opticsshop.DAO.repositories.BasketRepository;
import com.example.opticsshop.DAO.repositories.ProductRepository;
import com.example.opticsshop.services.BasketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BasketServiceTest {

    private BasketService basketService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private BasketRepository basketRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        basketService = new BasketService(basketRepository, productRepository);
    }

    @Test
    void testAddToBasket() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setPrice(100);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Basket basket = new Basket();
        basket.setProductId(productId);
        when(basketRepository.findByProductId(productId)).thenReturn(null);
        when(basketRepository.save(any(Basket.class))).thenReturn(basket);
        basketService.addToBasket(productId);
        verify(basketRepository, times(1)).save(any(Basket.class));
    }

    @Test
    void testRemoveFromCart() {
        Long productId = 1L;
        Basket basket = new Basket();
        basket.setAmount(3);
        when(basketRepository.findByProductId(productId)).thenReturn(basket);
        basketService.removeFromBasket(productId);
        verify(basketRepository, times(1)).save(any(Basket.class));
    }

    @Test
    void testGetCheckoutPrice() {
        Long productId1 = 1L;
        Long productId2 = 2L;
        Product product1 = new Product();
        product1.setId(productId1);
        product1.setPrice(100);
        Product product2 = new Product();
        product2.setId(productId2);
        product2.setPrice(200);
        Basket basket1 = new Basket();
        basket1.setCurr_product(product1);
        basket1.setAmount(4);
        Basket basket2 = new Basket();
        basket2.setCurr_product(product2);
        basket2.setAmount(2);
        List<Basket> basketList = Arrays.asList(basket1, basket2);
        when(basketRepository.findAll()).thenReturn(basketList);
        int expectedSum = 800;
        int actualSum = basketService.getCheckoutPrice();
        assertEquals(expectedSum, actualSum);
    }
}
