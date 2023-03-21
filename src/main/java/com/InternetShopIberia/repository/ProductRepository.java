package com.InternetShopIberia.repository;

import com.InternetShopIberia.dto.ProductDto;
import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Product findBypId(Long pId);

    public List<Product> findAllByCategoryId(Long Id);
}
