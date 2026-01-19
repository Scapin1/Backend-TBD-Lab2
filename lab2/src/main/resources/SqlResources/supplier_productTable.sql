CREATE TABLE IF NOT EXISTS Supplier_Product(
                                               supplier_idP BIGINT NOT NULL,
                                               product_idP BIGINT NOT NULL,
                                               quantity INTEGER,
                                               unit_purchase_price INTEGER,
                                               PRIMARY KEY (supplier_idP, product_idP),
    CONSTRAINT fk_supplier_product FOREIGN KEY (supplier_idP) REFERENCES Supplier(supplier_id),
    CONSTRAINT fk_product_supplier FOREIGN KEY (product_idP) REFERENCES Products(id_product)
    );

