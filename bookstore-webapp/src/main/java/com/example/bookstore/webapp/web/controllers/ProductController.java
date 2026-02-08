package com.example.bookstore.webapp.web.controllers;

import com.example.bookstore.webapp.clients.catelog.CatelogServiceClient;
import com.example.bookstore.webapp.clients.catelog.PagedResult;
import com.example.bookstore.webapp.clients.catelog.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final CatelogServiceClient catelogService;

    ProductController(CatelogServiceClient catelogService) {
        this.catelogService = catelogService;
    }

    @GetMapping
    String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    String showProductsPage(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        model.addAttribute("pageNo", page);
        return "products";
    }

    @GetMapping("/api/products")
    @ResponseBody
    PagedResult<Product> products(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        log.info("Fetching products for page: {}", page);
        return catelogService.getProducts(page);
    }
}