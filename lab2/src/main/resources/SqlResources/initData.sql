DO $$
BEGIN
-- ARCHIVO ÚNICO DE POBLAMIENTO DE DATOS SQL (TOTAL: 270 REGISTROS)

-- 1. POBLAMIENTO DE LA TABLA 'Stores' (8 Registros)
-----------------------------------------------------------------
INSERT INTO Stores (id_store, name_store, address_store, city_store) VALUES
                                                                           (1, 'Tienda Central Santiago', 'Av. Libertador 100', 'Santiago'),
                                                                           (2, 'Tienda Costera Valparaíso', 'Calle Prat 250', 'Valparaíso'),
                                                                           (3, 'Tienda Sur Concepción', 'Bulnes 50', 'Concepción'),
                                                                           (4, 'Tienda Norte Antofagasta', 'Avenida Balmaceda 300', 'Antofagasta'),
                                                                           (5, 'Tienda Cordillera Rancagua', 'Calle del Cobre 150', 'Rancagua'),
                                                                           (6, 'Tienda Iquique Playa', 'Paseo Costero 50', 'Iquique'),
-- Nuevas tiendas para volumen
                                                                           (7, 'Tienda Playera La Serena', 'Avenida del Mar 10', 'La Serena'),
                                                                           (8, 'Tienda Austral Temuco', 'Av. Alemania 700', 'Temuco');

-- 2. POBLAMIENTO DE LA TABLA 'Products' (15 Registros)
-----------------------------------------------------------------
INSERT INTO Products (id_product, name_product, description_product, price, SKU) VALUES
                                                                                     (1, 'Smartphone Galaxy X', 'Teléfono de alta gama, 128GB', 499.99, 'SMARTX128'),
                                                                                     (2, 'Laptop UltraBook Pro', 'Portátil ligero, 16GB RAM', 899.50, 'LAPUPB16'),
                                                                                     (3, 'Audífonos Inalámbricos Z', 'Cancelación de ruido, batería 20h', 79.99, 'AUDIWZ'),
                                                                                     (4, 'Mouse Óptico Ergonómico', 'Diseño para largas jornadas', 19.99, 'MOUSOPT'),
                                                                                     (5, 'Teclado Mecánico RGB', 'Switches Blue, full size', 65.00, 'TECMECRGB'),
                                                                                     (6, 'Monitor Curvo 27"', 'Pantalla LED, 144Hz', 249.99, 'MONCURV27'),
                                                                                     (7, 'Webcam HD 1080p', 'Micrófono integrado', 35.50, 'WEBCAMHD'),
                                                                                     (8, 'Disco SSD 1TB', 'Velocidad de lectura 550MB/s', 89.00, 'SSD1TB'),
                                                                                     (9, 'Router WiFi 6 Gigabit', 'Alto rendimiento, doble banda', 95.99, 'RTRWFG6'),
                                                                                     (10, 'Impresora Multifuncional Lazer', 'Color, dúplex automático', 175.50, 'IMPLAZERC'),
                                                                                     (11, 'Smartwatch Deportivo X4', 'Monitoreo de ritmo cardíaco y GPS', 120.00, 'SMARTWX4'),
                                                                                     (12, 'Adaptador USB-C a HDMI', 'Plug and play, 4K compatible', 15.00, 'ADAPUCHDMI'),
                                                                                     (13, 'Tablet Pro 11"', 'Pantalla Liquid Retina, 256GB', 699.00, 'TABPRO11'),
                                                                                     (14, 'Silla Gamer Ergonómica', 'Soporte lumbar ajustable', 189.99, 'SLLGMERGO'),
                                                                                     (15, 'Toner Impresora Negro', 'Rendimiento 2500 páginas', 45.00, 'TNRB2500');

-- 3. POBLAMIENTO DE LA TABLA 'Supplier' (6 Registros)
-----------------------------------------------------------------
INSERT INTO Supplier (supplier_id, supplier_name) VALUES
                                                      (101, 'Distribuidora Tecnológica Limitada'),
                                                      (102, 'Importadora Electrónica S.A.'),
                                                      (103, 'Global Components Chile'),
                                                      (104, 'Tecno Suministros Rápido'),
                                                      (105, 'Proveeduría Industrial Sur'),
                                                      (106, 'Componentes Avanzados SPA');

-- 4. POBLAMIENTO DE LA TABLA 'Users' (12 Registros) - Depende de Stores
-----------------------------------------------------------------
-- 1 Admin (NULL Store)
INSERT INTO Users (id_user, name_user, email_user, password_user, role, id_storeU) VALUES
    (1001, 'Ana Gutiérrez', 'ana.gutierrez@sys.com', 'hashedpass123', 'SUPERADMINISTRATOR', NULL);
-- Managers (T1, T4, T7)
INSERT INTO Users (id_user, name_user, email_user, password_user, role, id_storeU) VALUES
                                                                                       (1002, 'Benjamín Soto', 'benjamin.soto@stgo.com', 'hashedpass456', 'ADMINISTRATOR', 1),
                                                                                       (1007, 'Gloria Hernández', 'gloria.h@antofa.com', 'hashedpass999', 'ADMINISTRATOR', 4),
                                                                                       (1011, 'Horacio Izquierdo', 'horacio.i@serena.com', 'hashedpass111', 'ADMINISTRATOR', 7);
-- Employees
INSERT INTO Users (id_user, name_user, email_user, password_user, role, id_storeU) VALUES
                                                                                       (1003, 'Carla Díaz', 'carla.diaz@valpo.com', 'hashedpass789', 'EMPLOYEE', 2),
                                                                                       (1004, 'Daniel Flores', 'daniel.flores@valpo.com', 'hashedpass012', 'EMPLOYEE', 2),
                                                                                       (1005, 'Elena Rojas', 'elena.rojas@concep.com', 'hashedpass345', 'EMPLOYEE', 3),
                                                                                       (1006, 'Felipe Muñoz', 'felipe.munoz@concep.com', 'hashedpass678', 'EMPLOYEE', 3),
                                                                                       (1008, 'Héctor Ibarra', 'hector.i@ranca.com', 'hashedpass777', 'EMPLOYEE', 5),
                                                                                       (1009, 'Isabel Jaramillo', 'isabel.j@iquique.com', 'hashedpass666', 'EMPLOYEE', 6),
                                                                                       (1010, 'Javier Leiva', 'javier.l@iquique.com', 'hashedpass555', 'EMPLOYEE', 6),
                                                                                       (1012, 'Karla Núñez', 'karla.n@temuco.com', 'hashedpass222', 'EMPLOYEE', 8);

-- 5. POBLAMIENTO DE LA TABLA 'Supplier_Product' (15 Registros)
---------------------------------------------------------------------------------
-- NOTA: Se distribuyen los productos en grupos según el proveedor cuyo nombre comercial tiene más sentido.

INSERT INTO Supplier_Product (supplier_idP, product_idP, quantity, unit_purchase_price) VALUES

-- Proveedor 101: 'Distribuidora Tecnológica Limitada' (Periféricos)
(101, 3, 5000, 60.00),  -- Audífonos
(101, 4, 7500, 10.00),  -- Mouse
(101, 5, 3330, 50.00),  -- Teclado

-- Proveedor 102: 'Importadora Electrónica S.A.' (Dispositivos Móviles)
(102, 1, 2300, 405.00),  -- Smartphone
(102, 11, 4450, 90.00), -- Smartwatch
(102, 13, 1700, 555.00), -- Tablet

-- Proveedor 103: 'Global Components Chile' (Computación)
(103, 2, 1680, 755.00), -- Laptop
(103, 8, 2700, 70.00),  -- SSD

-- Proveedor 104: 'Tecno Suministros Rápido' (Suministros de Oficina)
(104, 10, 1930, 140.00), -- Impresora
(104, 15, 4600, 35.00),  -- Toner

-- Proveedor 105: 'Proveeduría Industrial Sur' (Redes y Ergonomía)
(105, 9, 3620, 75.00),  -- Router
(105, 14, 1190, 150.00), -- Silla Gamer

-- Proveedor 106: 'Componentes Avanzados SPA' (Periféricos de Video)
(106, 6, 2300, 205.00), -- Monitor
(106, 7, 3900, 25.00),  -- Webcam
(106, 12, 6400, 9.00);  -- Adaptador

-- 6. POBLAMIENTO DE LA TABLA 'Inventory' (120 Registros) - 15 Productos x 8 Tiendas
--------------------------------------------------------------------------------------
INSERT INTO Inventory (id_storeIn, id_productIn, stock_inventory) VALUES
-- T1 (Santiago)
(1, 1, 450), (1, 2, 320), (1, 3, 500), (1, 4, 380), (1, 5, 210), (1, 6, 410), (1, 7, 10), (1, 8, 270), (1, 9, 300), (1, 10, 250), (1, 11, 350), (1, 12, 1), (1, 13, 280), (1, 14, 9), (1, 15, 300),
-- T2 (Valparaíso)
(2, 1, 300), (2, 2, 250), (2, 3, 400), (2, 4, 310), (2, 5, 180), (2, 6, 350), (2, 7, 10), (2, 8, 200), (2, 9, 280), (2, 10, 200), (2, 11, 300), (2, 12, 10), (2, 13, 200), (2, 14, 5), (2, 15, 400),
-- T3 (Concepción)
(3, 1, 350), (3, 2, 280), (3, 3, 450), (3, 4, 400), (3, 5, 250), (3, 6, 300), (3, 7, 2), (3, 8, 150), (3, 9, 320), (3, 10, 220), (3, 11, 380), (3, 12, 2), (3, 13, 250), (3, 14, 5), (3, 15, 350),
-- T4 (Antofagasta)
(4, 1, 380), (4, 2, 290), (4, 3, 420), (4, 4, 350), (4, 5, 230), (4, 6, 370), (4, 7, 10), (4, 8, 250), (4, 9, 300), (4, 10, 200), (4, 11, 400), (4, 12, 4), (4, 13, 300), (4, 14, 5), (4, 15, 450),
-- T5 (Rancagua)
(5, 1, 400), (5, 2, 300), (5, 3, 450), (5, 4, 380), (5, 5, 200), (5, 6, 350), (5, 7, 5), (5, 8, 220), (5, 9, 250), (5, 10, 180), (5, 11, 350), (5, 12, 5), (5, 13, 220), (5, 14, 5), (5, 15, 380),
-- T6 (Iquique)
(6, 1, 330), (6, 2, 260), (6, 3, 480), (6, 4, 420), (6, 5, 270), (6, 6, 320), (6, 7, 3), (6, 8, 180), (6, 9, 280), (6, 10, 190), (6, 11, 380), (6, 12, 10), (6, 13, 270), (6, 14, 5), (6, 15, 420),
-- T7 (La Serena - NUEVA)
(7, 1, 360), (7, 2, 310), (7, 3, 430), (7, 4, 340), (7, 5, 220), (7, 6, 360), (7, 7, 3), (7, 8, 260), (7, 9, 290), (7, 10, 210), (7, 11, 370), (7, 12, 2), (7, 13, 310), (7, 14, 5), (7, 15, 330),
-- T8 (Temuco - NUEVA)
(8, 1, 340), (8, 2, 270), (8, 3, 410), (8, 4, 320), (8, 5, 190), (8, 6, 330), (8, 7, 2), (8, 8, 210), (8, 9, 260), (8, 10, 190), (8, 11, 310), (8, 12, 10), (8, 13, 240), (8, 14, 5), (8, 15, 390);

-- 7. POBLAMIENTO DE LA TABLA 'Transactions' (88 Registros) - Flujo Consistente y Redundante
-------------------------------------------------------------------------------------------
-- Transacciones pre-existentes (39 registros)
INSERT INTO Transactions (id_transaction, type_transaction, date_transaction, amount_product, id_product, id_storeOR, id_storeDE) VALUES
                                                                                                                                      (1, 'Receipt', '2025-10-25 09:00:00', 50, 1, NULL, 1), (2, 'Receipt', '2025-10-25 09:30:00', 100, 3, NULL, 1),
                                                                                                                                      (3, 'Receipt', '2025-10-25 10:00:00', 75, 5, NULL, 1), (4, 'Receipt', '2025-10-25 10:30:00', 50, 7, NULL, 1),
                                                                                                                                      (5, 'Receipt', '2025-10-25 11:00:00', 60, 8, NULL, 1), (11, 'Receipt', '2025-10-26 09:00:00', 100, 9, NULL, 4),
                                                                                                                                      (12, 'Receipt', '2025-10-26 09:30:00', 50, 10, NULL, 4), (13, 'Receipt', '2025-10-26 10:00:00', 150, 11, NULL, 4),
                                                                                                                                      (14, 'Receipt', '2025-10-26 10:30:00', 200, 12, NULL, 4), (15, 'Receipt', '2025-10-26 11:00:00', 70, 2, NULL, 4),
                                                                                                                                      (9, 'Transfer', '2025-10-25 16:00:00', 20, 7, 1, 3), (10, 'Transfer', '2025-10-25 16:30:00', 30, 8, 1, 3),
                                                                                                                                      (23, 'Transfer', '2025-10-26 17:00:00', 50, 5, 1, 6), (24, 'Transfer', '2025-10-26 17:30:00', 40, 11, 4, 5),
                                                                                                                                      (25, 'Transfer', '2025-10-26 18:00:00', 20, 2, 2, 3), (26, 'Sale', '2025-10-27 10:00:00', 2, 1, 1, NULL),
                                                                                                                                      (27, 'Sale', '2025-10-27 10:30:00', 5, 3, 1, NULL), (28, 'Sale', '2025-10-27 11:00:00', 1, 5, 1, NULL),
                                                                                                                                      (29, 'Sale', '2025-10-27 11:30:00', 3, 7, 1, NULL), (6, 'Sale', '2025-10-25 14:00:00', 5, 2, 2, NULL),
                                                                                                                                      (7, 'Sale', '2025-10-25 14:15:00', 10, 4, 2, NULL), (8, 'Sale', '2025-10-25 14:30:00', 2, 6, 2, NULL),
                                                                                                                                      (30, 'Sale', '2025-10-27 12:00:00', 4, 1, 2, NULL), (31, 'Sale', '2025-10-27 14:00:00', 3, 2, 3, NULL),
                                                                                                                                      (32, 'Sale', '2025-10-27 14:30:00', 5, 4, 3, NULL), (33, 'Sale', '2025-10-27 15:00:00', 1, 6, 3, NULL),
                                                                                                                                      (34, 'Sale', '2025-10-27 15:30:00', 2, 8, 3, NULL), (35, 'Sale', '2025-10-28 10:00:00', 4, 9, 4, NULL),
                                                                                                                                      (36, 'Sale', '2025-10-28 10:30:00', 1, 10, 4, NULL), (37, 'Sale', '2025-10-28 11:00:00', 6, 11, 4, NULL),
                                                                                                                                      (38, 'Sale', '2025-10-28 11:30:00', 10, 12, 4, NULL), (16, 'Sale', '2025-10-26 14:00:00', 3, 1, 5, NULL),
                                                                                                                                      (17, 'Sale', '2025-10-26 14:15:00', 8, 3, 5, NULL), (18, 'Sale', '2025-10-26 14:30:00', 1, 10, 5, NULL),
                                                                                                                                      (39, 'Sale', '2025-10-28 14:00:00', 2, 7, 5, NULL), (19, 'Sale', '2025-10-26 15:00:00', 5, 9, 6, NULL),
                                                                                                                                      (20, 'Sale', '2025-10-26 15:15:00', 3, 12, 6, NULL), (21, 'Sale', '2025-10-26 15:30:00', 2, 6, 6, NULL),
                                                                                                                                      (22, 'Sale', '2025-10-26 15:45:00', 4, 8, 6, NULL);

-- Transacciones ADICIONALES (49 registros - IDs 40-88)
-- FASE 3: Recepción y Transferencia (41 Transacciones)
INSERT INTO Transactions (id_transaction, type_transaction, date_transaction, amount_product, id_product, id_storeOR, id_storeDE) VALUES
-- Receipts (Proveedor -> Tienda)
(40, 'Receipt', '2025-10-29 08:00:00', 80, 13, NULL, 7), (41, 'Receipt', '2025-10-29 08:30:00', 40, 14, NULL, 7), -- T7: P13, P14
(42, 'Receipt', '2025-10-29 09:00:00', 50, 15, NULL, 8), (43, 'Receipt', '2025-10-29 09:30:00', 120, 4, NULL, 8), -- T8: P15, P4
-- Transfers (Movimiento entre tiendas para balancear)
(44, 'Transfer', '2025-10-29 11:00:00', 30, 10, 1, 7), -- T1 -> T7: P10
(45, 'Transfer', '2025-10-29 11:30:00', 25, 9, 4, 8),  -- T4 -> T8: P9
(46, 'Transfer', '2025-10-29 12:00:00', 15, 13, 7, 3), -- T7 -> T3: P13
(47, 'Transfer', '2025-10-29 12:30:00', 2, 14, 8, 5), -- T8 -> T5: P14
-- Múltiples Transferencias y Receipts adicionales (33 registros restantes para alcanzar 41 R/T)
(48, 'Receipt', '2025-10-29 13:00:00', 50, 1, NULL, 1), (49, 'Receipt', '2025-10-29 13:30:00', 30, 2, NULL, 2),
(50, 'Transfer', '2025-10-29 14:00:00', 15, 3, 2, 4), (51, 'Transfer', '2025-10-29 14:30:00', 20, 4, 3, 5),
(52, 'Receipt', '2025-10-29 15:00:00', 60, 5, NULL, 6), (53, 'Transfer', '2025-10-29 15:30:00', 5, 6, 6, 1),
(54, 'Receipt', '2025-10-29 16:00:00', 40, 7, NULL, 7), (55, 'Transfer', '2025-10-29 16:30:00', 10, 8, 5, 8),
(56, 'Receipt', '2025-10-29 17:00:00', 70, 9, NULL, 3), (57, 'Transfer', '2025-10-29 17:30:00', 8, 10, 4, 2),
(58, 'Receipt', '2025-10-29 18:00:00', 90, 11, NULL, 5), (59, 'Transfer', '2025-10-29 18:30:00', 4, 12, 6, 1),
(60, 'Receipt', '2025-10-30 08:00:00', 100, 13, NULL, 2), (61, 'Transfer', '2025-10-30 08:30:00', 2, 14, 6, 1),
(62, 'Receipt', '2025-10-30 09:00:00', 20, 15, NULL, 1), (63, 'Transfer', '2025-10-30 09:30:00', 5, 1, 2, 5),
(64, 'Receipt', '2025-10-30 10:00:00', 40, 2, NULL, 3), (65, 'Transfer', '2025-10-30 10:30:00', 10, 3, 4, 6),
(66, 'Receipt', '2025-10-30 11:00:00', 50, 4, NULL, 5), (67, 'Transfer', '2025-10-30 11:30:00', 8, 5, 3, 1),
(68, 'Receipt', '2025-10-30 12:00:00', 60, 6, NULL, 6), (69, 'Transfer', '2025-10-30 12:30:00', 3, 7, 5, 2),
(70, 'Receipt', '2025-10-30 13:00:00', 70, 8, NULL, 7), (71, 'Transfer', '2025-10-30 13:30:00', 9, 9, 6, 3),
(72, 'Receipt', '2025-10-30 14:00:00', 80, 10, NULL, 8), (73, 'Transfer', '2025-10-30 14:30:00', 11, 11, 7, 4),
(74, 'Receipt', '2025-10-30 15:00:00', 90, 12, NULL, 1), (75, 'Transfer', '2025-10-30 15:30:00', 7, 13, 8, 5),
(76, 'Receipt', '2025-10-30 16:00:00', 100, 14, NULL, 2), (77, 'Transfer', '2025-10-30 16:30:00', 6, 15, 1, 6),
(78, 'Receipt', '2025-10-30 17:00:00', 110, 1, NULL, 3), (79, 'Transfer', '2025-10-30 17:30:00', 5, 2, 2, 7),
(80, 'Receipt', '2025-10-30 18:00:00', 120, 3, NULL, 4);

-- FASE 4: Ventas Adicionales (8 Sales)
-- 4 Sales para T7 y 4 Sales para T8 (Mínimo de 4 ventas por tienda)
INSERT INTO Transactions (id_transaction, type_transaction, date_transaction, amount_product, id_product, id_storeOR, id_storeDE) VALUES
                                                                                                                                      (81, 'Sale', '2025-10-31 10:00:00', 5, 1, 7, NULL),  -- T7: P1
                                                                                                                                      (82, 'Sale', '2025-10-31 10:30:00', 2, 13, 7, NULL), -- T7: P13 (Tablet)
                                                                                                                                      (83, 'Sale', '2025-10-31 11:00:00', 8, 3, 7, NULL),  -- T7: P3
                                                                                                                                      (84, 'Sale', '2025-10-31 11:30:00', 1, 10, 7, NULL), -- T7: P10
                                                                                                                                      (85, 'Sale', '2025-10-31 14:00:00', 3, 4, 8, NULL),  -- T8: P4
                                                                                                                                      (86, 'Sale', '2025-10-31 14:30:00', 1, 15, 8, NULL), -- T8: P15 (Toner)
                                                                                                                                      (87, 'Sale', '2025-10-31 15:00:00', 4, 9, 8, NULL),  -- T8: P9
                                                                                                                                      (88, 'Sale', '2025-10-31 15:30:00', 2, 11, 8, NULL); -- T8: P11
-- FASE 5: Ventas Masivas para reducir Stock Total y probar consulta 2
-- (IDs 89-94)
-------------------------------------------------------------------------------------------
INSERT INTO Transactions (id_transaction, type_transaction, date_transaction, amount_product, id_product, id_storeOR, id_storeDE) VALUES
-- Objetivo: Dejar P14 (Silla Gamer) con < 50 unidades
(89, 'Sale', '2025-11-01 10:00:00', 45, 14, 7, NULL), -- T7
(90, 'Sale', '2025-11-01 10:15:00', 95, 14, 2, NULL), -- T2
(91, 'Sale', '2025-11-01 10:25:00', 7, 14, 1, NULL), -- T1

-- Objetivo: Dejar P7 (Webcam) con < 50 unidades
(92, 'Sale', '2025-11-01 10:30:00', 37, 7, 1, NULL), -- T1
(93, 'Sale', '2025-11-01 10:45:00', 43, 7, 7, NULL), -- T7
(94, 'Sale', '2025-11-01 11:00:00', 1, 7, 3, NULL);  -- T3

-- ============================================================
-- TRANSACCIONES Q1, Q2, Q3 (con ventas ajustadas)
-- ============================================================

-- Receipts Q1
INSERT INTO Transactions VALUES
                             (101, 'Receipt', '2025-01-15 09:00:00', 40, 1, NULL, 1),
                             (102, 'Receipt', '2025-02-10 10:00:00', 60, 2, NULL, 2),
                             (103, 'Receipt', '2025-03-05 11:00:00', 80, 3, NULL, 3),
                             (104, 'Receipt', '2025-03-20 09:30:00', 50, 4, NULL, 4),
                             (105, 'Receipt', '2025-03-25 08:45:00', 70, 5, NULL, 5);

-- Transfers Q1
INSERT INTO Transactions VALUES
                             (106, 'Transfer', '2025-01-20 14:00:00', 10, 1, 1, 2),
                             (107, 'Transfer', '2025-02-15 15:00:00', 20, 2, 2, 3),
                             (108, 'Transfer', '2025-03-01 16:00:00', 15, 3, 3, 4),
                             (109, 'Transfer', '2025-03-10 17:00:00', 25, 4, 4, 5),
                             (110, 'Transfer', '2025-03-28 18:00:00', 30, 5, 5, 6);

-- Sales Q1 (moderadas)
INSERT INTO Transactions VALUES
                             (111, 'Sale', '2025-01-25 10:00:00', 12, 1, 1, NULL),
                             (112, 'Sale', '2025-02-12 11:00:00', 15, 2, 2, NULL),
                             (113, 'Sale', '2025-02-28 12:00:00', 10, 3, 3, NULL),
                             (114, 'Sale', '2025-03-15 13:00:00', 18, 4, 4, NULL),
                             (115, 'Sale', '2025-03-22 14:00:00', 20, 5, 5, NULL);

-- ============================================================

-- Receipts Q2
INSERT INTO Transactions VALUES
                             (116, 'Receipt', '2025-04-05 09:00:00', 90, 6, NULL, 1),
                             (117, 'Receipt', '2025-04-20 10:00:00', 40, 7, NULL, 2),
                             (118, 'Receipt', '2025-05-10 11:00:00', 60, 8, NULL, 3),
                             (119, 'Receipt', '2025-06-01 09:30:00', 100, 9, NULL, 4),
                             (120, 'Receipt', '2025-06-15 08:45:00', 50, 10, NULL, 5);

-- Transfers Q2
INSERT INTO Transactions VALUES
                             (121, 'Transfer', '2025-04-12 14:00:00', 12, 6, 1, 2),
                             (122, 'Transfer', '2025-05-05 15:00:00', 18, 7, 2, 3),
                             (123, 'Transfer', '2025-05-25 16:00:00', 22, 8, 3, 4),
                             (124, 'Transfer', '2025-06-10 17:00:00', 30, 9, 4, 5),
                             (125, 'Transfer', '2025-06-20 18:00:00', 25, 10, 5, 6);

-- Sales Q2 (altas – pico)
INSERT INTO Transactions VALUES
                             (126, 'Sale', '2025-04-18 10:00:00', 60, 6, 1, NULL),
                             (127, 'Sale', '2025-05-08 11:00:00', 50, 7, 2, NULL),
                             (128, 'Sale', '2025-05-30 12:00:00', 70, 8, 3, NULL),
                             (129, 'Sale', '2025-06-12 13:00:00', 80, 9, 4, NULL),
                             (130, 'Sale', '2025-06-25 14:00:00', 90, 10, 5, NULL);

-- ============================================================

-- Receipts Q3
INSERT INTO Transactions VALUES
                             (131, 'Receipt', '2025-07-05 09:00:00', 70, 11, NULL, 1),
                             (132, 'Receipt', '2025-07-20 10:00:00', 50, 12, NULL, 2),
                             (133, 'Receipt', '2025-08-10 11:00:00', 80, 13, NULL, 3),
                             (134, 'Receipt', '2025-08-25 09:30:00', 60, 14, NULL, 4),
                             (135, 'Receipt', '2025-09-15 08:45:00', 90, 15, NULL, 5);

-- Transfers Q3
INSERT INTO Transactions VALUES
                             (136, 'Transfer', '2025-07-12 14:00:00', 10, 11, 1, 2),
                             (137, 'Transfer', '2025-07-28 15:00:00', 20, 12, 2, 3),
                             (138, 'Transfer', '2025-08-15 16:00:00', 15, 13, 3, 4),
                             (139, 'Transfer', '2025-09-05 17:00:00', 25, 14, 4, 5),
                             (140, 'Transfer', '2025-09-22 18:00:00', 30, 15, 5, 6);

-- Sales Q3 (bajas – caída)
INSERT INTO Transactions VALUES
                             (141, 'Sale', '2025-07-18 10:00:00', 8, 11, 1, NULL),
                             (142, 'Sale', '2025-08-08 11:00:00', 6, 12, 2, NULL),
                             (143, 'Sale', '2025-08-30 12:00:00', 5, 13, 3, NULL),
                             (144, 'Sale', '2025-09-12 13:00:00', 7, 14, 4, NULL),
                             (145, 'Sale', '2025-09-25 14:00:00', 4, 15, 5, NULL);

-- ============================================================
-- FIN BLOQUE Q1, Q2, Q3
-- ============================================================


-- ---------------------------------------------------------------------
-- FIN DEL ARCHIVO DE POBLAMIENTO
-- ---------------------------------------------------------------------


END $$ LANGUAGE plpgsql;

