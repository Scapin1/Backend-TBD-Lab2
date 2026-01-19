package com.example.demo.controllers;

import com.example.demo.entities.DistributionCenter;
import com.example.demo.entities.Stores;
import com.example.demo.services.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logistics")
public class LogisticsController {

    @Autowired
    private LogisticsService logisticsService;

    // 1. Sugerencia de Transferencia Óptima
    @GetMapping("/optimal-transfer")
    public Stores getOptimalTransferStore(@RequestParam Long expectingStoreId, @RequestParam Long productId,
            @RequestParam int quantity) {
        return logisticsService.getOptimalTransferStore(expectingStoreId, productId, quantity);
    }

    // 2. Análisis de Cobertura de Tiendas (Buffer 10km)
    @GetMapping("/coverage/{storeId}")
    public List<Stores> getCoverageAnalysis(@PathVariable Long storeId) {
        return logisticsService.getNearingStores(storeId);
    }

    // 3. Asignación de Centro de Distribución
    @GetMapping("/assign-distribution-center/{storeId}")
    public DistributionCenter assignDistributionCenter(@PathVariable Long storeId) {
        return logisticsService.assignBestDistributionCenter(storeId);
    }

    // 4. Cálculo de Distancia de Traslado
    @GetMapping("/transfer-distance")
    public Double calculateTransferDistance(@RequestParam Long originStoreId, @RequestParam Long destStoreId) {
        return logisticsService.calculateTransferDistance(originStoreId, destStoreId);
    }
}
