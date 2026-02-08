package com.example.bookstore.webapp.clients.orders;

public record OrderSummary(
        String orderNumber,
        OrderStatus status
) {}
