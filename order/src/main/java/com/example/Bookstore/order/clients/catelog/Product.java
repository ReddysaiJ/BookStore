package com.example.Bookstore.order.clients.catelog;

import java.math.BigDecimal;

public record Product(String code,
                      String name,
                      String description,
                      String imageUrl,
                      BigDecimal price) {}
