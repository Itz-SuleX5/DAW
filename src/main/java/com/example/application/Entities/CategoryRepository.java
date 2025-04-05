package com.example.application.Entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserId(Long userId);
    List<Category> findByNameContainingIgnoreCase(String name);




}
