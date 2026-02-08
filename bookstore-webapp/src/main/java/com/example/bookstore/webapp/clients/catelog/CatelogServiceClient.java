package com.example.bookstore.webapp.clients.catelog;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface CatelogServiceClient {

    @GetExchange("/catelog/api/products")
    PagedResult<Product> getProducts(@RequestParam int page);

    @GetExchange("/catelog/api/products/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable int code);
}
