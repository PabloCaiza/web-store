package com.quesito.webstore.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service

public class ProductAsyncService {
    @Async("asyncExecutor")
    public CompletableFuture<List<Product1>> getProducts1() throws InterruptedException {
        Thread.sleep(1000);
        return CompletableFuture.completedFuture(Arrays.asList(new Product1(1, "Product 1"),
                new Product1(2, "Product 2"),
                new Product1(3,"Product 3"),
                new Product1(4,"Product 4")));
    }
    @Async("asyncExecutor")
    public CompletableFuture<List<Product1>> getProducts2() throws InterruptedException {
        Thread.sleep(3000);
        return CompletableFuture.completedFuture(Arrays.asList(new Product1(5, "Product 5"),
                new Product1(6, "Product 6"),
                new Product1(7,"Product 7"),
                new Product1(8,"Product 8")));
    }
    @Async("asyncExecutor")
    public CompletableFuture<List<Product1>> getProducts3() throws InterruptedException {
        Thread.sleep(2000);
        return CompletableFuture.completedFuture(Arrays.asList(new Product1(9, "Product 9"),
                new Product1(10, "Product 10"),
                new Product1(11,"Product 11"),
                new Product1(12,"Product 12")));
    }

}
