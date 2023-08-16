package com.example.newsUser.model.repository;

import com.example.newsUser.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URepository extends JpaRepository<User, Long> {
}
