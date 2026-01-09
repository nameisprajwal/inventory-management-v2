package com.korber.inventory.inventory_service.repository;

import com.korber.inventory.inventory_service.entity.ProductBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductBatchRepository extends JpaRepository<ProductBatch, Integer> {

    List<ProductBatch> findByProductIdOrderByExpiryDateAsc(Integer productId);
}


