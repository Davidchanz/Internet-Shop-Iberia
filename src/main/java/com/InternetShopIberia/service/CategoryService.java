package com.InternetShopIberia.service;

import com.InternetShopIberia.model.Category;
import com.InternetShopIberia.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public Category findRootCategory(){
        var categories = categoryRepository.findAll();
        var root = categories.get(categories.size()-1);
        return categoryRepository.findById(root.getId()).orElseThrow(() -> new RuntimeException("No categories!"));
    }

    public Category findCategoryById(Long id){
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("No Category with Id="+id));
    }

    public List<Category> findCategoryTitleLike(String title){
        return categoryRepository.findCategoryTitleLike(title);
    }
}
