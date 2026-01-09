package com.korber.inventory.inventory_service.controller;

import com.korber.inventory.inventory_service.dto.InventoryUpdateRequest;
import com.korber.inventory.inventory_service.dto.ProductBatchDto;
import com.korber.inventory.inventory_service.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductBatchDto>> getInventory(@PathVariable Integer productId) {
        List<ProductBatchDto> batches = inventoryService.getInventoryForProduct(productId);
        return ResponseEntity.ok(batches);
    }


    @PostMapping("/update")
    public ResponseEntity<String> updateInventory(@RequestBody InventoryUpdateRequest request) {
        try {
            inventoryService.updateInventory(request);
            return ResponseEntity.ok("Inventory updated successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}


