CREATE TABLE IF NOT EXISTS Stores(
                                     id_store SERIAL PRIMARY KEY,
                                     name_store VARCHAR(255) UNIQUE,
    address_store VARCHAR(255),
    city_store VARCHAR(255)
    );
