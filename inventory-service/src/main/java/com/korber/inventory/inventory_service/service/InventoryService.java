package com.korber.inventory.inventory_service.service;

import com.korber.inventory.inventory_service.dto.InventoryUpdateRequest;
import com.korber.inventory.inventory_service.dto.ProductBatchDto;

import java.util.List;


public interface InventoryService {

    List<ProductBatchDto> getInventoryForProduct(Integer productId);

    void updateInventory(InventoryUpdateRequest request);
}


