-- Script simplificado: Creación de tablas y objetos espaciales adicionales
-- Asume que PostGIS ya está habilitado y Stores ya tiene address_store como GEOGRAPHY


-- 1. Crear tabla distribution_centers
CREATE TABLE IF NOT EXISTS "distribution_centers" (
    "id_center" SERIAL PRIMARY KEY,
    "name_center" VARCHAR(255),
    "location" GEOGRAPHY(POINT, 4326)
);

-- 1.5 Autocorrección y Limpieza Forzada:
-- Truncar tablas para asegurar que initData.sql se ejecute siempre y tengamos datos frescos (incluyendo contraseñas).
DO $$
BEGIN
    RAISE NOTICE 'Limpiando tablas para forzar recarga de datos...';
    TRUNCATE TABLE "stores", "products", "users", "supplier", "inventory", "transactions", "supplier_product", "distribution_centers" RESTART IDENTITY CASCADE;

    -- Si es necesario convertir a GEOGRAPHY (caso borde)
    IF EXISTS (
        SELECT 1 
        FROM information_schema.columns 
        WHERE table_name = 'stores' 
        AND column_name = 'address_store' 
        AND (data_type = 'character varying' OR data_type = 'text')
    ) THEN
        ALTER TABLE "stores" 
        ALTER COLUMN "address_store" TYPE GEOGRAPHY(POINT, 4326) 
        USING NULL;
    END IF;
END $$;

-- 2. Índices Espaciales
CREATE INDEX IF NOT EXISTS "idx_stores_location" ON "stores" USING GIST ("address_store");
CREATE INDEX IF NOT EXISTS "idx_centers_location" ON "distribution_centers" USING GIST ("location");

-- 3. FK id_assigned_center en Stores
ALTER TABLE "stores" ADD COLUMN IF NOT EXISTS "id_assigned_center" INTEGER;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.table_constraints WHERE constraint_name = 'fk_store_center') THEN
        ALTER TABLE "stores" 
        ADD CONSTRAINT "fk_store_center" 
        FOREIGN KEY ("id_assigned_center") 
        REFERENCES "distribution_centers" ("id_center");
    END IF;
END $$;
