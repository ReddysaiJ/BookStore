package com.example.Bookstore.order.domain.models;

public record OrderSummary(
        String orderNumber,
        OrderStatus status
) {}
