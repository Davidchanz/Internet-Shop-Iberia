package com.InternetShopIberia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SearchResult {
     private List<SearchResultItem> results;
}
