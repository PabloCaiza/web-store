package com.quesito.webstore.service;

import com.quesito.webstore.domain.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    void updateAllStock();
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByFilter(Map<String, List<String>> filterParams);
    Product getProductById(String productID);
    void addProduct(Product product);
}
