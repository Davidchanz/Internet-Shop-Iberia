package com.InternetShopIberia.repository;

import com.InternetShopIberia.model.Product;

import java.util.List;

public interface UserProductListRepositorySorting {
    List<Product> findAllProductsInUserListByIdSortBy(Long Id, String sortBy, String sortTo);
}
