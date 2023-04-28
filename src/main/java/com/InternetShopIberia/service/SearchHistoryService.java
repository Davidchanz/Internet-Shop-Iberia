package com.InternetShopIberia.service;

import com.InternetShopIberia.model.SearchHistory;
import com.InternetShopIberia.repository.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchHistoryService {
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    public List<SearchHistory> findAllSearchHistoryLikeRequest(String request){
        return searchHistoryRepository.findAllSearchHistoryLikeRequest(request);
    }

    public SearchHistory addSearchHistory(SearchHistory searchHistory){
        return searchHistoryRepository.save(searchHistory);
    }
}
