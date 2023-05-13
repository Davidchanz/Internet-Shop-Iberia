package com.InternetShopIberia.repository.impl;

import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.model.UserProductList;
import com.InternetShopIberia.repository.UserProductListRepositorySorting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;

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

    @Override
    public List<Product> findAllProductsInUserListByIdSortBy(Long Id, Pageable pageable) {
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

        query.
                select(product).
                where(cb.or(predicates.toArray(new Predicate[0]))).
                orderBy(QueryUtils.toOrders(pageable.getSort(), product, cb));

        return entityManager.createQuery(query).
                setMaxResults(pageable.getPageSize()).
                setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).
                getResultList();
    }

    @Override
    public List<Product> findAllProductsInUserListById(Long Id) {
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

        query.select(product).where(cb.or(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Product> findAllProductsInUserListById(Long Id, Pageable pageable) {
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

        query.select(product).where(cb.or(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).
                setMaxResults(pageable.getPageSize()).
                setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).
                getResultList();
    }
}
