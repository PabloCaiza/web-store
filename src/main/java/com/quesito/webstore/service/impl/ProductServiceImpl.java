package com.quesito.webstore.service.impl;

import com.quesito.webstore.domain.Product;
import com.quesito.webstore.repository.ProductRepository;
import com.quesito.webstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void updateAllStock() {
        productRepository.getProducts()
                .stream()
                .forEach(product -> {
                    if (product.getUnitsInStock() < 500) {
                        productRepository.updateStock(product.getProductId(), product.getUnitsInStock() + 1000);
                    }
                });
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getProducts();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.getProductsByCategory(category);
    }

    @Override
    public List<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
        return productRepository.getProductsByFilter(filterParams);
    }

    @Override
    public Product getProductById(String productID) {
        return  productRepository.getProductById(productID);
    }

    @Override
    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }
}
