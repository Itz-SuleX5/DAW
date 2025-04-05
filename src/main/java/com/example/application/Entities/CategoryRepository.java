package com.example.application.Entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUser_IdUser(Long idUser);
    List<Category> findByCategoryNameContainingIgnoreCase(String categoryName);


}
