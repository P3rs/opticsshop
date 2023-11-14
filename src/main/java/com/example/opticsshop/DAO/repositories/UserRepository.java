package com.example.opticsshop.DAO.repositories;

import com.example.opticsshop.DAO.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
