package com.korber.inventory.order_service.service;

import com.korber.inventory.order_service.dto.InventoryUpdateRequest;
import com.korber.inventory.order_service.dto.OrderItemRequest;
import com.korber.inventory.order_service.dto.OrderRequest;
import com.korber.inventory.order_service.dto.OrderResponse;
import com.korber.inventory.order_service.entity.Order;
import com.korber.inventory.order_service.entity.OrderItem;
import com.korber.inventory.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final String inventoryBaseUrl;

    public OrderServiceImpl(OrderRepository orderRepository,
                            RestTemplate restTemplate,
                            @Value("${inventory.service.base-url}") String inventoryBaseUrl) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
        this.inventoryBaseUrl = inventoryBaseUrl;
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        try {

            for (OrderItemRequest item : request.getItems()) {
                InventoryUpdateRequest inventoryUpdate = new InventoryUpdateRequest();
                inventoryUpdate.setProductId(item.getProductId());
                inventoryUpdate.setQuantity(item.getQuantity());

                ResponseEntity<String> response = restTemplate.exchange(
                        inventoryBaseUrl + "/inventory/update",
                        HttpMethod.POST,
                        new HttpEntity<>(inventoryUpdate),
                        String.class
                );

                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new IllegalStateException("Inventory update failed: " + response.getBody());
                }
            }


            Order order = new Order();
            order.setCustomerName(request.getCustomerName());
            order.setStatus("CREATED");
            order.setCreatedAt(LocalDateTime.now());

            List<OrderItem> items = new ArrayList<>();
            for (OrderItemRequest itemRequest : request.getItems()) {
                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setProductId(itemRequest.getProductId());
                item.setQuantity(itemRequest.getQuantity());
                items.add(item);
            }
            order.setItems(items);

            Order saved = orderRepository.save(order);
            return new OrderResponse(saved.getId(), saved.getStatus(), "Order created successfully");
        } catch (RestClientException | IllegalStateException ex) {
            // Handle communication errors or insufficient stock.
            throw new IllegalStateException("Failed to place order: " + ex.getMessage(), ex);
        }
    }
}


