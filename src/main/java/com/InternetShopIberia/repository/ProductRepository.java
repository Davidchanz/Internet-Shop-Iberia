package com.InternetShopIberia.repository;

import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositorySorting {
    public Product findBypId(Long pId);

    public List<Product> findAllByCategoryId(Long Id);

    public Page<Product> findAllByCategoryId(Long Id, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    public List<Product> searchByNameLike(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    public Page<Product> searchByNameLike(@Param("name") String name, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    public Page<Product> searchByNameLikeSortBy(@Param("name") String name, Pageable pageable);
}
