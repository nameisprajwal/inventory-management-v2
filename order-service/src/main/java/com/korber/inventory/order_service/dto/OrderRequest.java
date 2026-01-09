package com.korber.inventory.order_service.dto;

import java.util.List;

/**
 * Request DTO for placing an order.
 */
public class OrderRequest {

    private String customerName;
    private List<OrderItemRequest> items;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}


