package com.InternetShopIberia.repository;

import com.InternetShopIberia.model.SearchHistory;
import com.InternetShopIberia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    @Query("SELECT h FROM SearchHistory h WHERE h.user = :user AND h.searchRequest LIKE %:request%")
    public List<SearchHistory> findAllSearchHistoryLikeRequestByUser(@Param("request") String request, @Param("user") User user);
}
