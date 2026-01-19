package com.example.demo.services;

import com.example.demo.Dtos.SummaryStockStore;
import com.example.demo.entities.Stores;
import com.example.demo.repositories.StoreRepository;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    //Finders

    public List<Stores> getAll() {return storeRepository.findAll();}
    public List<Stores> getByCity(String city) {return storeRepository.findByCity(city);}
    public Stores getById(Long id) {return storeRepository.findById(id);}
    public Stores getByName(String name) {return storeRepository.findByName(name);}
    public Stores getByAddress(String address) {return storeRepository.findByAddress(address);}

    // Create

    public int createStore(Stores store) {return storeRepository.save(store);}

    // Update

    public int updateStore(Stores store) {return storeRepository.update(store);}

    // Delete

    public int deleteStore(Long id) {return storeRepository.delete(id);}

    //consulta 10
    public List<SummaryStockStore> summaryStockStoreTotalStores(){
        refreshResumenStockTienda();
        return storeRepository.getResumenStockTienda();
    }

    public void refreshResumenStockTienda() {
        storeRepository.refreshResumenStockTienda();
    }
}
