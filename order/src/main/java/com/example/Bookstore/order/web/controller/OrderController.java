package com.example.Bookstore.order.web.controller;

import com.example.Bookstore.order.domain.OrderNotFoundException;
import com.example.Bookstore.order.domain.OrderService;
import com.example.Bookstore.order.domain.SecurityService;
import com.example.Bookstore.order.domain.models.CreateOrderRequest;
import com.example.Bookstore.order.domain.models.CreateOrderResponse;
import com.example.Bookstore.order.domain.models.OrderDTO;
import com.example.Bookstore.order.domain.models.OrderSummary;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@SecurityRequirement(name = "security_auth")
class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final SecurityService securityService;

    OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request){
        String username = securityService.getLoginUsername();
        log.info("Creating order for user: {}", username);
        return orderService.createOrder(username, request);
    }

    @GetMapping
    List<OrderSummary> getOrders(){
        String username = securityService.getLoginUsername();
        log.info("Fetching orders for user: {}", username);
        return orderService.findOrders(username);
    }

    @GetMapping("/{orderNumber}")
    OrderDTO getOrder(@PathVariable(value = "orderNumber") String orderNumber){
        log.info("Fetching order by id: {}", orderNumber);
        String username = securityService.getLoginUsername();
        return orderService.findUserOrder(username, orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(orderNumber));
    }
}
