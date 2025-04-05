package com.example.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.Entities.Category;
import com.example.application.Entities.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    
    // Create
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
    
    // Read All
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    // Read by User
    public List<Category> getCategoriesByUserId(Long idUser) {
        return categoryRepository.findByUser_IdUser(idUser);
    }
    
    // Read One
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
    
    // Search by name
    public List<Category> searchCategoriesByName(String categoryName) {
        return categoryRepository.findByCategoryNameContainingIgnoreCase(categoryName);
    }
    
    // Update
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }
    
    // Delete
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    
    // Check if category exists
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }
}
