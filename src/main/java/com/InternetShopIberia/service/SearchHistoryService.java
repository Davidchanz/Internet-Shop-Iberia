package com.InternetShopIberia.service;

import com.InternetShopIberia.model.SearchHistory;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.repository.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchHistoryService {
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    public List<SearchHistory> findAllSearchHistoryLikeRequestByUser(String request, User user){
        return searchHistoryRepository.findAllSearchHistoryLikeRequestByUser(request, user);
    }

    public SearchHistory addSearchHistory(SearchHistory searchHistory){
        return searchHistoryRepository.save(searchHistory);
    }
}
