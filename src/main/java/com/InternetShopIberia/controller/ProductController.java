package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.*;
import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.service.CategoryService;
import com.InternetShopIberia.service.ProductService;
import com.InternetShopIberia.service.UserProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserProductListService userProductListService;

    @Autowired
    private CategoryService categoryService;

    @Value("${pageSize}")
    private int pageSize;

    @Value("${maxPages}")
    private int maxPages;

    @GetMapping("/products")
    public String showProductCategoryPage(@RequestParam Map<String,String> allRequestParams, Model model){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());
        String categoryId = allRequestParams.get("categoryId");
        String searchRequest = allRequestParams.get("searchRequest");
        String collectionId = allRequestParams.get("collectionId");
        String sortBy = allRequestParams.get("sortBy");
        String sortTo = allRequestParams.get("sortTo");
        String page = allRequestParams.get("page");
        allRequestParams.remove("categoryId");
        allRequestParams.remove("searchRequest");
        allRequestParams.remove("collectionId");
        allRequestParams.remove("sortBy");
        allRequestParams.remove("sortTo");
        allRequestParams.remove("page");

        Sort sort = null;
        if(sortBy != null && sortTo != null)
            sort = new Sort("", sortBy, sortTo, true);
        var allProducts = getAllProducts(categoryId, collectionId, searchRequest, allRequestParams, sort);
        TreeMap<String, TreeSet<String>> details = new TreeMap<>();
        for(var product: allProducts.getProducts()){
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
        String[] sortBys = resourceBundle.getString("sorting.sortBy").split(",");
        String[] sortNames = resourceBundle.getString("sorting.name").split(",");
        List<Sort> sorts = new ArrayList<>();
        for(int i = 0; i < sortNames.length; i++){
            if(sortBys[i].equals(sortBy))
                sorts.add(new Sort(sortNames[i], sortBys[i], sortTo, true));
            else
                sorts.add(new Sort(sortNames[i], sortBys[i], sortTo, false));
        }

        sortList.setSorts(sorts);
        sortList.setSortTo(sortTo);

        model.addAttribute("filters", filters);
        model.addAttribute("sortingList", sortList);

        var search = resourceBundle.getString("title.search");
        var badRequest = resourceBundle.getString("title.search.badRequest");
        var category = resourceBundle.getString("title.category");
        var collection = resourceBundle.getString("title.collection");

        var filteredProducts = getAllProducts(categoryId, collectionId, searchRequest, allRequestParams, sort);
        int productCount = getFilteredProducts(filteredProducts.getProducts(), allRequestParams).getProducts().size();
        int pageNumbers = (int) Math.ceil(productCount / (double) pageSize);
        List<PaginationDto> pagination = new ArrayList<>();

        if(page == null)
            page = "1";
        if(Integer.parseInt(page) > pageNumbers) {
            page = String.valueOf(pageNumbers);
        }
        if(page.equals("0"))
            page = "1";

        int currentStartPage = Integer.parseInt(page) - maxPages;
        int currentLastPage = Integer.parseInt(page) + maxPages;

        for(int i = currentStartPage; i <= currentLastPage; i++){
            if(i < 1)
                continue;
            if(i > pageNumbers)
                break;
            if (i == Integer.parseInt(page))
                pagination.add(new PaginationDto(true, i));
            else
                pagination.add(new PaginationDto(false, i));
        }

        Pageable pageable = PageRequest.of(Integer.parseInt(page)-1, pageSize);

        var pagedProducts = getPagedProducts(categoryId, collectionId, searchRequest, allRequestParams, sort, pageable);

        model.addAttribute("products", pagedProducts);

        model.addAttribute("pagination", pagination);

        if (searchRequest != null) {
            model.addAttribute("title", search + " '"+searchRequest+"'");
        } else if(categoryId != null) {
            model.addAttribute("title", category + " '"+categoryService.findCategoryById(Long.parseLong(categoryId)).getTitle()+"'");
        } else {
            model.addAttribute("title", collection + " '"+userProductListService.findUserProductListById(Long.parseLong(collectionId)).getName()+"'");
        }
        if(pagedProducts.getProducts().isEmpty())
            model.addAttribute("title", badRequest);

        return "products";
    }

    @GetMapping("/products/filter")
    public RedirectView filterProducts(@RequestParam Map<String, String> allRequestParams, RedirectAttributes redirectAttributes){
        RedirectView redirectView;
        StringBuilder filterStr = new StringBuilder();

        allRequestParams.remove("page");//refresh page when filtering

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
        details.forEach((name, values) -> {
            Filter filter = new Filter();
            filter.setName(name);
            List<FilterValue> filterValues = new ArrayList<>();
            values.forEach(value -> {
                if(allRequestParams.get(name) == null)
                    filterValues.add(new FilterValue(value, false));
                else if(allRequestParams.get(name).equals(value)) {
                    filterValues.add(new FilterValue(value, true));
                } else
                    filterValues.add(new FilterValue(value, false));
            });
            filter.setValues(filterValues);
            filters.getFilters().add(filter);
        });
        return filters;
    }

    private ProductList getAllProducts(String categoryId, String collectionId, String searchRequest, Map<String,String> allRequestParams, Sort sort){
        List<Product> productList;
        if(sort == null) {
            if (searchRequest != null) {
                productList = productService.getAllProductsNameLike(searchRequest);
            } else if(categoryId != null) {
                productList = productService.getAllProductsInCategoryById(Long.parseLong(categoryId));
            } else {
                productList = userProductListService.findAllProductsInUserListById(Long.parseLong(collectionId));
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

        return new ProductList(productList);
    }

    private ProductList getPagedProducts(String categoryId, String collectionId, String searchRequest, Map<String,String> allRequestParams, Sort sort, Pageable pageable){
        var products = getAllProducts(categoryId, collectionId, searchRequest, allRequestParams, sort).getProducts();
        var productList = getFilteredProducts(products, allRequestParams).getProducts();
        int max = (pageable.getPageNumber() * pageSize) + pageSize;
        if(max > productList.size()-1)
            max = productList.size()-1;
        if(productList.size() > pageSize){
            productList = productList.subList(pageable.getPageNumber() * pageSize, max);
        }
        return new ProductList(productList);
    }

    private ProductList getFilteredProducts(List<Product> productList, Map<String,String> filters){
        ProductList filteredProducts = new ProductList();
        filteredProducts.setProducts(new ArrayList<>());
        for(var product: productList) {
            AtomicInteger filtersCount = new AtomicInteger(0);
            AtomicBoolean productFit = new AtomicBoolean(true);
            for(var detail: product.getDetails()) {
                AtomicBoolean detailFit = new AtomicBoolean(false);
                filters.forEach((name, value) -> {
                    if(detail.getName().equals(name)){
                        filtersCount.incrementAndGet();
                        if(!detail.getValue().equals(value)) {
                            detailFit.set(false);
                        }else {
                            detailFit.set(true);
                        }
                        productFit.set(productFit.get() && detailFit.get());
                    }
                });
            }
            if(productFit.get() && filtersCount.get() == filters.size()){
                filteredProducts.getProducts().add(product);
            }
        }
        return filteredProducts;
    }
}
