package com.example.opticsshop.services;

import com.example.opticsshop.DAO.models.Basket;
import com.example.opticsshop.DAO.models.Product;
import com.example.opticsshop.DAO.repositories.BasketRepository;
import com.example.opticsshop.DAO.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasketService {
    @Autowired
    private final BasketRepository basketRepository;

    @Autowired
    private final ProductRepository productRepository;


    public List<Basket> getBasket() {
        return basketRepository.findAll();
    }
    public void addToBasket(Long id) {
        Basket basketForCheck = basketRepository.findByProductId(id);
        Basket basketItem = new Basket();
        if(basketForCheck == null) {
            basketItem.setProductId(id);
            Product pr = productRepository.findById(id).orElse(null);
            basketItem.setCurr_product(pr);
            basketItem.setAmount(basketItem.getAmount()+1);
            basketRepository.save(basketItem);
        }
        else {
            basketForCheck.setAmount(basketForCheck.getAmount()+1);
            basketRepository.save(basketForCheck);
        }
    }

    public void removeFromBasket(Long id) {
        Basket basket = basketRepository.findByProductId(id);
        if (basket != null) {
            if(basket.getAmount() == 1)
                basketRepository.delete(basket);
            else if(basket.getAmount() > 1) {
                basket.setAmount(basket.getAmount()-1);
                basketRepository.save(basket);
            }
        }
    }


    public int getCheckoutPrice() {
        int sum = 0;
        List<Basket> allBasket = getBasket();
        for(Basket item : allBasket) {
            sum+=item.getCurr_product().getPrice() * item.getAmount();
        }
        return sum;
    }
}
