package com.quesito.webstore.repository;

import com.quesito.webstore.domain.Product;

import java.util.List;
import java.util.Map;

public interface ProductRepository {

    List<Product> getProducts();
     void updateStock(String productId, long noOfUnits);
     List<Product> getProductsByCategory(String category);
     List<Product> getProductsByFilter(Map<String,List<String>> filterParams);
     Product getProductById(String productID);
     void addProduct(Product product);
}
