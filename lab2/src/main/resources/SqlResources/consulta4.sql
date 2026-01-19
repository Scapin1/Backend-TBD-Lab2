SELECT stores.name_store, amount_product , products.name_product FROM (
    Select
        id_storeor,
        amount_product,
        id_product,
        ROW_NUMBER() OVER (PARTITION BY id_storeor ORDER BY amount_product DESC) as number_row
    FROM transactions
    WHERE type_transaction = 'Sale')
AS ranked
INNER JOIN stores
        ON ranked.id_storeor = stores.id_store
INNER JOIN products
        ON ranked.id_product = products.id_product
WHERE number_row <= 3;