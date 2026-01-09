package com.korber.inventory.inventory_service.service;

import com.korber.inventory.inventory_service.dto.InventoryUpdateRequest;
import com.korber.inventory.inventory_service.dto.ProductBatchDto;
import com.korber.inventory.inventory_service.entity.ProductBatch;
import com.korber.inventory.inventory_service.inventory.InventoryStrategy;
import com.korber.inventory.inventory_service.inventory.InventoryStrategyFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryStrategyFactory inventoryStrategyFactory;

    public InventoryServiceImpl(InventoryStrategyFactory inventoryStrategyFactory) {
        this.inventoryStrategyFactory = inventoryStrategyFactory;
    }

    @Override
    public List<ProductBatchDto> getInventoryForProduct(Integer productId) {
        InventoryStrategy strategy = inventoryStrategyFactory.getDefaultStrategy();
        List<ProductBatch> batches = strategy.getBatchesForProduct(productId);
        return batches.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateInventory(InventoryUpdateRequest request) {
        InventoryStrategy strategy = inventoryStrategyFactory.getDefaultStrategy();
        strategy.deductInventory(request);
    }

    private ProductBatchDto toDto(ProductBatch batch) {
        ProductBatchDto dto = new ProductBatchDto();
        dto.setBatchId(batch.getBatchId());
        dto.setProductId(batch.getProductId());
        dto.setProductName(batch.getProductName());
        dto.setQuantity(batch.getQuantity());
        dto.setExpiryDate(batch.getExpiryDate());
        return dto;
    }
}


