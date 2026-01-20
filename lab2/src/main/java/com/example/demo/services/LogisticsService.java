package com.example.demo.services;

import com.example.demo.entities.DistributionCenter;
import com.example.demo.entities.Stores;
import com.example.demo.repositories.DistributionCenterRepository;
import com.example.demo.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private DistributionCenterRepository distributionCenterRepository;

    // 1. Sugerencia de Transferencia Óptima
    public Stores getOptimalTransferStore(Long requestingStoreId, Long productId, int quantity) {
        // Encontrar la tienda con stock más cercana
        return storeRepository.findNearestWithStock(requestingStoreId, productId, quantity);
    }

    // 2. Análisis de Cobertura de Tiendas
    public List<Stores> getNearingStores(Long storeId) {
        // Buffer de 10 km (radius = 10.0)
        return storeRepository.findStoresWithinBuffer(storeId, 10.0);
    }

    // 3. Asignación de Centro de Distribución
    public DistributionCenter assignBestDistributionCenter(Long storeId) {

        Stores store = storeRepository.findById(storeId);
        if (store == null || store.getAddress_store() == null)
            return null;

        double[] coords = parseWktPoint(store.getAddress_store());
        if (coords == null)
            return null;

        return distributionCenterRepository.findNearest(coords[1], coords[0]); // lat, lon
    }

    // 4. Cálculo de Distancia de Traslado
    public Double calculateTransferDistance(Long originStoreId, Long destStoreId) {
        // Retorna distancia en metros
        return storeRepository.calculateDistance(originStoreId, destStoreId);
    }

    private double[] parseWktPoint(String wkt) {
        // Simple parser for POINT(lon lat)
        try {
            String content = wkt.replace("POINT(", "").replace(")", "");
            String[] parts = content.split(" ");
            double lon = Double.parseDouble(parts[0]);
            double lat = Double.parseDouble(parts[1]);
            return new double[] { lon, lat };
        } catch (Exception e) {
            return null;
        }
    }
}
