package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.*;
import com.InternetShopIberia.model.Category;
import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.service.CategoryService;
import com.InternetShopIberia.service.ProductService;
import com.InternetShopIberia.service.UserProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserProductListService userProductListService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String showProductCategoryPage(@RequestParam Map<String,String> allRequestParams, Model model){
        String categoryId = allRequestParams.get("categoryId");
        String searchRequest = allRequestParams.get("searchRequest");
        String collectionId = allRequestParams.get("collectionId");
        String sortBy = allRequestParams.get("sortBy");
        String sortTo = allRequestParams.get("sortTo");
        allRequestParams.remove("categoryId");
        allRequestParams.remove("searchRequest");
        allRequestParams.remove("collectionId");
        allRequestParams.remove("sortBy");
        allRequestParams.remove("sortTo");

        Sort sort = null;
        if(sortBy != null && sortTo != null)
            sort = new Sort(sortBy, sortTo, true);
        var filteredProducts = getProducts(categoryId, collectionId, searchRequest, allRequestParams, sort);
        TreeMap<String, TreeSet<String>> details = new TreeMap<>();
        for(var product: filteredProducts.getProducts()){
            for(var detail: product.getDetails()){
                if(details.get(detail.getName()) == null){
                    var value = new TreeSet<String>();
                    value.add(detail.getValue());
                    details.put(detail.getName(), value);
                }else {
                    details.get(detail.getName()).add(detail.getValue());
                }
            }
        }
        var filters = getFilters(details, allRequestParams);

        SortList sortList = new SortList();
        List<String> sortNames = List.of("name", "price", "rating");
        List<Sort> sorts = new ArrayList<>();
        for(var sortName: sortNames){
            if(sortName.equals(sortBy))
                sorts.add(new Sort(sortName, sortTo, true));
            else
                sorts.add(new Sort(sortName, sortTo, false));
        }

        sortList.setSorts(sorts);
        sortList.setSortTo(sortTo);

        model.addAttribute("filters", filters);
        model.addAttribute("products", filteredProducts);
        model.addAttribute("sortingList", sortList);

        if (searchRequest != null) {
            model.addAttribute("title", "Result by Search Request '"+searchRequest+"'");
        } else if(categoryId != null) {
            model.addAttribute("title", "Category '"+categoryService.findCategoryById(Long.parseLong(categoryId)).getTitle()+"'");
        } else {
            model.addAttribute("title", "Collection '"+userProductListService.findUserProductListById(Long.parseLong(collectionId)).getName()+"'");
        }

        return "products";
    }

    @GetMapping("/products/filter")
    public RedirectView filterProducts(@RequestParam Map<String,String> allRequestParams, RedirectAttributes redirectAttributes){

        RedirectView redirectView;
        StringBuilder filterStr = new StringBuilder();
        allRequestParams.forEach((name, value) -> {
            if(!name.equals("categoryId") && !name.equals("searchRequest") && !name.equals("collectionId"))
                filterStr.append("&").append(name).append("=").append(value);
        });
        if(allRequestParams.get("categoryId") != null)
            redirectView = new RedirectView("/products?categoryId="+allRequestParams.get("categoryId") + filterStr.toString(), true);
        else if(allRequestParams.get("searchRequest") != null)
            redirectView = new RedirectView("/products?searchRequest="+allRequestParams.get("searchRequest") + filterStr.toString(), true);
        else
            redirectView = new RedirectView("/products?collectionId="+allRequestParams.get("collectionId") + filterStr.toString(), true);

        return redirectView;
    }

    private FilterList getFilters(TreeMap<String, TreeSet<String>> details, Map<String,String> allRequestParams){
        FilterList filters = new FilterList();
        filters.setFilters(new ArrayList<>());
        details.forEach((s, strings) -> {
            Filter filter = new Filter();
            filter.setName(s);
            List<FilterValue> filterValues = new ArrayList<>();
            strings.forEach(value -> {
                if(allRequestParams.containsValue(value)) {
                    filterValues.add(new FilterValue(value, true));
                }
                else
                    filterValues.add(new FilterValue(value, false));
            });
            filter.setValues(filterValues);
            filters.getFilters().add(filter);
        });
        return filters;
    }

    private ProductList getProducts(String categoryId, String collectionId, String searchRequest, Map<String,String> allRequestParams, Sort sort){
        List<Product> productList = null;
        if(sort == null) {
            if (searchRequest != null) {
                productList = productService.getAllProductsNameLike(searchRequest);
            } else if(categoryId != null) {
                productList = productService.getAllProductsInCategoryById(Long.parseLong(categoryId));
            } else {
                productList = userProductListService.findUserProductListById(Long.parseLong(collectionId)).getProducts().stream().toList();
            }
        }else {
            if (searchRequest != null) {
                productList = productService.getAllProductsNameLikeSortBy(searchRequest, sort);
            } else if(categoryId != null){
                productList = productService.getAllProductsInCategoryByIdSortBy(Long.parseLong(categoryId), sort);
            } else {
                productList = userProductListService.findAllProductsInUserListByIdSortBy(Long.parseLong(collectionId), sort);
            }
        }

        ProductList filteredProducts = new ProductList();
        filteredProducts.setProducts(new ArrayList<>());
        for(var product: productList) {
            AtomicBoolean fit = new AtomicBoolean(true);
            for(var detail: product.getDetails()) {
                allRequestParams.forEach((name, value) -> {
                    if(detail.getName().equals(name)){
                        if(!detail.getValue().equals(value)) {
                            fit.set(false);
                            return;
                        }
                    }
                });
                if(!fit.get()){
                    break;
                }
            }
            if(fit.get()){
                filteredProducts.getProducts().add(product);
            }
        }
        return filteredProducts;
    }
}
