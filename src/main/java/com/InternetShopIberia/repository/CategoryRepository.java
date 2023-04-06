package com.InternetShopIberia.repository;

import com.InternetShopIberia.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.title LIKE %:title%")
    List<Category> findCategoryTitleLike(String title);
}
