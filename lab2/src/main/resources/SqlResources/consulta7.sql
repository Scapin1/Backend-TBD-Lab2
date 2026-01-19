/*
.- RETURN TRIGGER AS $$ ... // ...$$
Indica que es una funcion triger, no es llamada manualmente, donde Los símbolos $$ delimitan el cuerpo
de la función como un bloque de texto, permitiendo incluir múltiples líneas y comillas simples
sin necesidad de duplicacion, a diferencia de las comillas simples tradicionales ('').

.- BEGIN ...//... END
Delimita el la logica que se ejecutara. Ademas si la función se ejecuta dentro de una transacción activa
(como ocurre normalmente en el backend con Spring Boot), y ocurre un error durante la ejecución,
toda la operación se revierte automáticamente, manteniendo la base de datos en su estado anterior.
*/
CREATE OR REPLACE FUNCTION update_inventory_after_transaction()
RETURNS TRIGGER AS $$
BEGIN
    /* Solo aplicar si el tipo de transaccion es 'Sale' o 'Receipt' */
    IF NEW.type_transaction = 'Sale' THEN
        /* Si es una venta se resta el stock del inventario */
UPDATE Inventory
SET stock_inventory = stock_inventory - NEW.amount_product
WHERE id_storeIn = NEW.id_storeOR
  AND id_productIn = NEW.id_product
  AND stock_inventory >= NEW.amount_product;
IF NOT FOUND THEN
            RAISE EXCEPTION 'Stock insuficiente para producto % en tienda %', NEW.id_product, NEW.id_storeOR;
END IF;

    ELSIF NEW.type_transaction = 'Receipt' THEN
        /*
        Si ya el producto en el inventario, sumar stock
        */
UPDATE Inventory
SET stock_inventory = stock_inventory + NEW.amount_product
WHERE id_storeIn = NEW.id_storeDE
  AND id_productIn = NEW.id_product;
/*
Si no existe producto en inventario, agregar el producto con inventario inicial
igual a la cantidad indicada en la transaccion
*/
IF NOT FOUND THEN
            INSERT INTO Inventory (id_storeIn, id_productIn, stock_inventory)
            VALUES (NEW.id_storeDE, NEW.id_product, NEW.amount_product);
END IF;
END IF;

RETURN NEW;

EXCEPTION
    WHEN OTHERS THEN
        RAISE NOTICE 'Error en trigger: %', SQLERRM;
        RAISE EXCEPTION 'Falló la actualización de inventario';
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_update_inventory ON Transactions;

CREATE TRIGGER trg_update_inventory
    AFTER INSERT ON Transactions
    FOR EACH ROW
    EXECUTE FUNCTION update_inventory_after_transaction();