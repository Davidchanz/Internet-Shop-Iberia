package com.InternetShopIberia.repository.impl;

import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.model.UserProductList;
import com.InternetShopIberia.repository.UserProductListRepositorySorting;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class UserProductListRepositorySortingImpl implements UserProductListRepositorySorting {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAllProductsInUserListByIdSortBy(Long Id, String sortBy, String sortTo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserProductList> userProductListCriteriaQuery = cb.createQuery(UserProductList.class);
        Root<UserProductList> userProductListRoot = userProductListCriteriaQuery.from(UserProductList.class);
        Path<String> collectionId = userProductListRoot.get("id");
        userProductListCriteriaQuery.select(userProductListRoot).where(cb.equal(collectionId, Id));
        var collections = entityManager.createQuery(userProductListCriteriaQuery).getResultList();
        var currentCollection = collections.get(0);

        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        Path<String> productId = product.get("id");
        for(var collectionProductId: currentCollection.getProducts()) {
            predicates.add(cb.equal(productId, collectionProductId.getId()));
        }

        if (sortTo.equals("asc"))
            query.
            select(product).
            where(cb.or(predicates.toArray(new Predicate[0]))).
            orderBy(cb.asc(product.get(sortBy)));
        else
            query.
            select(product).
            where(cb.or(predicates.toArray(new Predicate[0]))).
            orderBy(cb.desc(product.get(sortBy)));

        return entityManager.createQuery(query).getResultList();
    }
}
