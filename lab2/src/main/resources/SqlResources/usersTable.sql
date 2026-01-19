CREATE TABLE IF NOT EXISTS Users(
                                    id_user SERIAL PRIMARY KEY,
                                    name_user VARCHAR(255),
    email_user VARCHAR(255) UNIQUE NOT NULL,
    password_user VARCHAR(255) NOT NULL,
    role VARCHAR(255),
    id_storeU BIGINT,
    CONSTRAINT fk_store_user FOREIGN KEY (id_storeU) REFERENCES Stores(id_store)
    );
