package com.quesito.webstore.async;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1/products/")
@RequiredArgsConstructor
public class ProductController1 {

    private final ProductService1 productService;
    private  final  ProductAsyncService productAsyncService;

    @GetMapping
    public List<Product1> getAllProducts() throws InterruptedException {
        List<Product1> productList1 = productService.getProducts1();
        List<Product1> productList2 = productService.getProducts2();
        List<Product1> productList3 = productService.getProducts3();

        return Stream
                .of(productList1, productList2, productList3)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());


    }

    @GetMapping("/async")
    public List<Product1> getAllProductsAsync() throws InterruptedException, ExecutionException {
        CompletableFuture<List<Product1>> c1 = productAsyncService.getProducts1();
        CompletableFuture<List<Product1>> c2 = productAsyncService.getProducts2();
        CompletableFuture<List<Product1>> c3 = productAsyncService.getProducts3();
        CompletableFuture.allOf(c1,c2,c3)
                .join();

        return Stream
                .of(c1.get(), c2.get(), c3.get())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());


    }
}
