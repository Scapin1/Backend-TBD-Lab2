CREATE OR REPLACE PROCEDURE transferir_inventario(
    p_id_product BIGINT,
    p_id_store_origin BIGINT,
    p_id_store_destiny BIGINT,
    p_quantity INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Validation 1: Check if sufficient stock exists
    IF NOT EXISTS (
        SELECT 1
        FROM Inventory
        WHERE id_productin = p_id_product
            AND id_storein = p_id_store_origin
            AND stock_inventory >= p_quantity
    ) THEN
        RAISE EXCEPTION 'Insufficient stock in the origin store';
    END IF;

    -- Validation 2: Ensure the stores are different
    IF p_id_store_origin = p_id_store_destiny THEN
        RAISE EXCEPTION 'The origin and destination stores cannot be the same';
    END IF;

    -- Step 1: Update the stock of the origin store
    UPDATE Inventory
    SET stock_inventory = stock_inventory - p_quantity
    WHERE id_productin = p_id_product AND id_storein = p_id_store_origin;

    -- Step 2: Update the stock of the destination store
    INSERT INTO Inventory (id_productin, id_storein, stock_inventory)
    VALUES (p_id_product, p_id_store_destiny, p_quantity)
    ON CONFLICT (id_productin, id_storein)
    DO UPDATE SET stock_inventory = Inventory.stock_inventory + p_quantity;

    -- Step 3: Record the transaction
    INSERT INTO Transactions (type_transaction, id_storeOR, id_storeDE, id_product, amount_product, date_transaction)
    VALUES ('Transfer', p_id_store_origin, p_id_store_destiny, p_id_product, p_quantity, CURRENT_DATE);
END;
$$;
