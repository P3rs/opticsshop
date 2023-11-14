package com.example.opticsshop.DAO.repositories;

import com.example.opticsshop.DAO.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitle(String title);
    List<Product> findByBrand(String brand);
    List<Product> findByBrandAndTitle(String brand, String title);
}
