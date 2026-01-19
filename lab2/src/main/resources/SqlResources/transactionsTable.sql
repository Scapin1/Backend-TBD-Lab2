/*
Transactions Types:
'Sale', 'Transfer', 'Receipt'
*/
CREATE TABLE IF NOT EXISTS Transactions(
                                           id_transaction SERIAL,
                                           type_transaction VARCHAR(255),
    date_transaction DATE,
    amount_product INTEGER,
    id_product BIGINT,
    id_storeOR BIGINT,
    id_storeDE BIGINT,
    CONSTRAINT fk_storeOrigin_transaction FOREIGN KEY (id_storeOR) REFERENCES Stores(id_store),
    CONSTRAINT fk_storeDestiny_transaction FOREIGN KEY (id_storeDE) REFERENCES Stores(id_store),
    CONSTRAINT fk_product_transaction FOREIGN KEY (id_product) REFERENCES Products(id_product)
    );
