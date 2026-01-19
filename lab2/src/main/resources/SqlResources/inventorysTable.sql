
CREATE TABLE IF NOT EXISTS Inventory(
                                        id_storeIn BIGINT NOT NULL,
                                        id_productIn BIGINT NOT NULL,
                                        stock_inventory INTEGER,
                                        PRIMARY KEY (id_storeIn, id_productIn),
    CONSTRAINT fk_store_inventory FOREIGN KEY (id_storeIn) REFERENCES Stores(id_store),
    CONSTRAINT fk_product_inventory  FOREIGN KEY (id_productIn) REFERENCES Products(id_product)
    );
