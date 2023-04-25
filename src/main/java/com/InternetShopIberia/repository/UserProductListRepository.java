package com.InternetShopIberia.repository;

import com.InternetShopIberia.controller.ProductController;
import com.InternetShopIberia.model.UserProductList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserProductListRepository extends JpaRepository<UserProductList, Long>, UserProductListRepositorySorting {
}
