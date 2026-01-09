package com.korber.inventory.inventory_service.inventory;

import com.korber.inventory.inventory_service.dto.InventoryUpdateRequest;
import com.korber.inventory.inventory_service.entity.ProductBatch;
import com.korber.inventory.inventory_service.repository.ProductBatchRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component("fefoInventoryStrategy")
public class FefoInventoryStrategy implements InventoryStrategy {

    private final ProductBatchRepository productBatchRepository;

    public FefoInventoryStrategy(ProductBatchRepository productBatchRepository) {
        this.productBatchRepository = productBatchRepository;
    }

    @Override
    public List<ProductBatch> getBatchesForProduct(Integer productId) {
        return productBatchRepository.findByProductIdOrderByExpiryDateAsc(productId);
    }

    @Override
    @Transactional
    public void deductInventory(InventoryUpdateRequest request) {
        List<ProductBatch> batches = productBatchRepository.findByProductIdOrderByExpiryDateAsc(request.getProductId());

        int remainingToDeduct = request.getQuantity() == null ? 0 : request.getQuantity();
        for (ProductBatch batch : batches) {
            if (remainingToDeduct <= 0) {
                break;
            }
            int available = batch.getQuantity() == null ? 0 : batch.getQuantity();
            if (available <= 0) {
                continue;
            }
            if (available >= remainingToDeduct) {
                batch.setQuantity(available - remainingToDeduct);
                remainingToDeduct = 0;
            } else {
                batch.setQuantity(0);
                remainingToDeduct -= available;
            }
        }

        if (remainingToDeduct > 0) {
            throw new IllegalStateException("Insufficient stock for productId=" + request.getProductId());
        }

        productBatchRepository.saveAll(batches);
    }
}


