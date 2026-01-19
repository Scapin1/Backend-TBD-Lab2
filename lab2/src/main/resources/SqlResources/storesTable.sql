-- Habilitar PostGIS antes de usar tipos geométricos
CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS Stores(
	id_store SERIAL PRIMARY KEY,
	name_store VARCHAR(255),
	address_store GEOGRAPHY(POINT, 4326),
	city_store VARCHAR(255)
);
