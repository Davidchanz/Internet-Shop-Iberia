package com.InternetShopIberia.repository.impl;

import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.repository.ProductRepositorySorting;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositorySortingImpl implements ProductRepositorySorting {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> searchByNameLikeSortBy(String name, String sortBy, String sortTo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        Path<String> nameLike = product.get("name");
        if(sortTo.equals("asc"))
            query.select(product).
                    where(cb.like(nameLike, "%"+name+"%")).
                    orderBy(cb.asc(product.get(sortBy)));
        else
            query.select(product).
                    where(cb.like(nameLike, "%"+name+"%")).
                    orderBy(cb.desc(product.get(sortBy)));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Product> findAllByCategoryIdSortBy(Long Id, String sortBy, String sortTo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        Path<String> categoryId = product.get("category");
        if(sortTo.equals("asc"))
            query.select(product).
                    where(cb.equal(categoryId, Id)).
                    orderBy(cb.asc(product.get(sortBy)));
        else
            query.select(product).
                    where(cb.equal(categoryId, Id)).
                    orderBy(cb.desc(product.get(sortBy)));

        return entityManager.createQuery(query).getResultList();
    }
}
