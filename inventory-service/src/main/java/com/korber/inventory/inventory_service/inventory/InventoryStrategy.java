package com.korber.inventory.inventory_service.inventory;

import com.korber.inventory.inventory_service.dto.InventoryUpdateRequest;
import com.korber.inventory.inventory_service.entity.ProductBatch;

import java.util.List;

public interface InventoryStrategy {


    List<ProductBatch> getBatchesForProduct(Integer productId);

    void deductInventory(InventoryUpdateRequest request);
}


