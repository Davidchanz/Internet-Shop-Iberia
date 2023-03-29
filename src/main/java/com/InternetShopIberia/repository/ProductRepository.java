package com.InternetShopIberia.repository;

import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Product findBypId(Long pId);

    public List<Product> findAllByCategoryId(Long Id);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    public List<Product> searchByNameLike(@Param("name") String name);
}
