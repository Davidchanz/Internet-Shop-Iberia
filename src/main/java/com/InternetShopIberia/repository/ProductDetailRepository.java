package com.InternetShopIberia.repository;

import com.InternetShopIberia.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
}
