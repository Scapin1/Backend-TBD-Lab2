CREATE TABLE IF NOT EXISTS Products(
	id_product SERIAL PRIMARY KEY,
	name_product VARCHAR(255),
	description_product VARCHAR(255),
	price INTEGER,
	SKU VARCHAR(255) UNIQUE NOT NULL
);


