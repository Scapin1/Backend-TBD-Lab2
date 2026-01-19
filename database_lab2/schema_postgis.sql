-- Script de adaptación espacial para TBD-Lab2
-- Este script asume que las tablas base ya existen (Stores, etc.)

-- 1. Habilitar la extensión PostGIS
CREATE EXTENSION IF NOT EXISTS postgis;

-- 2. Modificar la tabla Stores para agregar ubicación
-- Se usa GEOGRAPHY(POINT, 4326) para cálculos precisos en metros sobre la esfera terrestre
ALTER TABLE "stores" ADD COLUMN "location" GEOGRAPHY(POINT, 4326);

-- 3. Crear índice espacial para Stores
CREATE INDEX "idx_stores_location" ON "stores" USING GIST ("location");

-- 4. Crear tabla Centers (Centros de Distribución) si no existe
-- Se asume que esta entidad es nueva y separada de Stores
CREATE TABLE IF NOT EXISTS "distribution_centers" (
    "id_center" SERIAL PRIMARY KEY,
    "name_center" VARCHAR(255),
    "location" GEOGRAPHY(POINT, 4326)
);

-- 5. Crear índice espacial para Distribution Centers
CREATE INDEX "idx_centers_location" ON "distribution_centers" USING GIST ("location");

-- 6. Agregar columna id_assigned_center a Stores para la asignación logística
ALTER TABLE "stores" ADD COLUMN "id_assigned_center" INTEGER;

-- 7. Agregar FK para la relación Store -> Distribution Center
ALTER TABLE "stores" 
ADD CONSTRAINT "fk_store_center" 
FOREIGN KEY ("id_assigned_center") 
REFERENCES "distribution_centers" ("id_center");
