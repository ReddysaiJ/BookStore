package com.example.bookstore.webapp.web.controllers;

import com.example.bookstore.webapp.clients.orders.*;
import com.example.bookstore.webapp.services.SecurityServices;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderServiceClient orderServiceClient;
    private final SecurityServices securityServices;

    OrderController(OrderServiceClient orderServiceClient, SecurityServices securityServices) {
        this.orderServiceClient = orderServiceClient;
        this.securityServices = securityServices;
    }

    @GetMapping("/cart")
    String cart() {
        return "cart";
    }

    @PostMapping("/api/orders")
    @ResponseBody
    OrderConfirmationDTO createOrder(@Valid @RequestBody CreateOrderRequest orderRequest) {
        log.info("Creating order: {}", orderRequest);
        String accessToken = securityServices.getAccessToken();
        Map<String, ?> headers = Map.of("Authorization", "Bearer " + accessToken);
        return orderServiceClient.createOrder(headers, orderRequest);
    }

    @GetMapping("/orders/{orderNumber}")
    String showOrderDetails(@PathVariable String orderNumber, Model model) {
        model.addAttribute("orderNumber", orderNumber);
        return "order_details";
    }

    @GetMapping("/api/orders/{orderNumber}")
    @ResponseBody
    OrderDTO getOrder(@PathVariable String orderNumber) {
        log.info("Fetching order details for orderNumber: {}", orderNumber);
        String accessToken = securityServices.getAccessToken();
        Map<String, ?> headers = Map.of("Authorization", "Bearer " + accessToken);
        return orderServiceClient.getOrder(headers, orderNumber);
    }

    @GetMapping("/orders")
    String showOrders() {
        return "orders";
    }

    @GetMapping("/api/orders")
    @ResponseBody
    List<OrderSummary> getOrders() {
        log.info("Fetching orders");
        String accessToken = securityServices.getAccessToken();
        Map<String, ?> headers = Map.of("Authorization", "Bearer " + accessToken);
        return orderServiceClient.getOrders(headers);
    }

}