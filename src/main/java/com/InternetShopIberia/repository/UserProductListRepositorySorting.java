package com.InternetShopIberia.repository;

import com.InternetShopIberia.model.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserProductListRepositorySorting {
    List<Product> findAllProductsInUserListByIdSortBy(Long Id, String sortBy, String sortTo);

    List<Product> findAllProductsInUserListByIdSortBy(Long Id, Pageable pageable);

    List<Product> findAllProductsInUserListById(Long Id);

    List<Product> findAllProductsInUserListById(Long Id, Pageable pageable);
}
