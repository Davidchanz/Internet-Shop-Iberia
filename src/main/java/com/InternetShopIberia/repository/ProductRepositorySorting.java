package com.InternetShopIberia.repository;

import com.InternetShopIberia.model.Product;

import java.util.List;

public interface ProductRepositorySorting {
    List<Product> searchByNameLikeSortBy(String name, String sortBy, String sortTo);

    List<Product> findAllByCategoryIdSortBy(Long Id, String sortBy, String sortTo);
}
