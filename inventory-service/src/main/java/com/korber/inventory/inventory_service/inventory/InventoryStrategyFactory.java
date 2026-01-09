package com.korber.inventory.inventory_service.inventory;

import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class InventoryStrategyFactory {

    private final Map<String, InventoryStrategy> strategiesByName;

    public InventoryStrategyFactory(Map<String, InventoryStrategy> strategiesByName) {
        this.strategiesByName = strategiesByName;
    }


    public InventoryStrategy getDefaultStrategy() {
        // Bean name matches the name defined on FefoInventoryStrategy.
        InventoryStrategy strategy = strategiesByName.get("fefoInventoryStrategy");
        if (strategy == null) {
            throw new IllegalStateException("Default FEFO inventory strategy not configured");
        }
        return strategy;
    }
}


