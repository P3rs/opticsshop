package com.example.opticsshop.DAO.repositories;

import com.example.opticsshop.DAO.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    Basket findByProductId(Long productId);
}
