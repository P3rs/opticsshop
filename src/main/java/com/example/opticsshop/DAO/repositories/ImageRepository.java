package com.example.opticsshop.DAO.repositories;

import com.example.opticsshop.DAO.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
