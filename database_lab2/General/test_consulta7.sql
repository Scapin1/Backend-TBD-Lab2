/*Verificar la cambios en inventario*/
/*Primero ver como esta el inventario y luego realzar los insert para verificar el funcionamiento del trigger*/
select * from inventory;

INSERT INTO Transactions (type_transaction, date_transaction, amount_product, id_product, id_storeOR, id_storeDE) VALUES
('Receipt', '2025-07-05', 20, 1, NULL, 2);

INSERT INTO Transactions (type_transaction, date_transaction, amount_product, id_product, id_storeOR, id_storeDE) VALUES
('Sale', '2025-08-18', 3, 1, 2, NULL);