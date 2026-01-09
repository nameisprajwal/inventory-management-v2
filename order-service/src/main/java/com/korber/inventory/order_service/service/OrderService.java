package com.korber.inventory.order_service.service;

import com.korber.inventory.order_service.dto.OrderRequest;
import com.korber.inventory.order_service.dto.OrderResponse;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest request);
}


