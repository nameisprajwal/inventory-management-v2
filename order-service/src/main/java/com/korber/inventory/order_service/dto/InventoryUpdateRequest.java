package com.korber.inventory.order_service.dto;

/**
 * DTO used to call Inventory Service over HTTP.
 * Mirrors the contract of the inventory-service InventoryUpdateRequest.
 */
public class InventoryUpdateRequest {

    private Integer productId;
    private Integer quantity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}


