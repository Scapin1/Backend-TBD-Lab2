# DOCUMENTACIÓN DE BASE DE DATOS

## ESQUEMA DE BASE DE DATOS

Este documento describe el esquema completo de la base de datos del sistema de gestión de inventario multi-tienda.

### TABLAS

#### 1. Products
Almacena la información de los productos del sistema.

**Campos:**
- `id_product` (SERIAL, PRIMARY KEY): Identificador único del producto
- `name_product` (VARCHAR(255)): Nombre del producto
- `description_product` (VARCHAR(255)): Descripción del producto
- `price` (INTEGER): Precio del producto
- `SKU` (VARCHAR(255), UNIQUE, NOT NULL): Código SKU único del producto

#### 2. Stores
Almacena la información de las tiendas físicas.

**Campos:**
- `id_store` (SERIAL, PRIMARY KEY): Identificador único de la tienda
- `name_store` (VARCHAR(255)): Nombre de la tienda
- `address_store` (VARCHAR(255)): Dirección de la tienda
- `city_store` (VARCHAR(255)): Ciudad donde se encuentra la tienda

#### 3. Users
Almacena la información de los usuarios del sistema.

**Campos:**
- `id_user` (SERIAL, PRIMARY KEY): Identificador único del usuario
- `name_user` (VARCHAR(255)): Nombre del usuario
- `email_user` (VARCHAR(255), UNIQUE, NOT NULL): Correo electrónico único del usuario
- `password_user` (VARCHAR(255), NOT NULL): Contraseña encriptada del usuario
- `role` (VARCHAR(255)): Rol del usuario (ADMINISTRATOR, MANAGER, EMPLOYEE, etc.)
- `storeU_id` (BIGINT): Identificador de la tienda a la que pertenece el usuario (puede ser NULL)

**Relaciones:**
- `fk_store_user`: Clave foránea que referencia a `Stores(id_store)`

#### 4. Inventory
Almacena el inventario de productos por tienda. Utiliza una clave primaria compuesta.

**Campos:**
- `id_storeIn` (BIGINT, NOT NULL): Identificador de la tienda
- `id_productIn` (BIGINT, NOT NULL): Identificador del producto
- `stock_inventory` (INTEGER): Cantidad de stock disponible

**Clave Primaria Compuesta:**
- PRIMARY KEY (`id_storeIn`, `id_productIn`)

**Relaciones:**
- `fk_store_inventory`: Clave foránea que referencia a `Stores(id_store)`
- `fk_product_inventory`: Clave foránea que referencia a `Products(id_product)`

#### 5. Transactions
Registra todas las transacciones del sistema (ventas, transferencias y recepciones).

**Campos:**
- `id_transaction` (SERIAL): Identificador único de la transacción
- `type_transaction` (VARCHAR(255)): Tipo de transacción ('Sale', 'Transfer', 'Receipt')
- `date_transaction` (DATE): Fecha de la transacción
- `amount_product` (INTEGER): Cantidad de productos involucrados
- `id_product` (BIGINT): Identificador del producto
- `id_storeOR` (BIGINT): Identificador de la tienda origen
- `id_storeDE` (BIGINT): Identificador de la tienda destino

**Relaciones:**
- `fk_storeOrigin_transaction`: Clave foránea que referencia a `Stores(id_store)`
- `fk_storeDestiny_transaction`: Clave foránea que referencia a `Stores(id_store)`
- `fk_product_transaction`: Clave foránea que referencia a `Products(id_product)`

**Nota:** Los tipos de transacción son:
- **'Sale'**: Venta de productos (usa `id_storeOR`)
- **'Transfer'**: Transferencia entre tiendas (usa `id_storeOR` y `id_storeDE`)
- **'Receipt'**: Recepción de productos (usa `id_storeDE`)

#### 6. Supplier
Almacena la información de los proveedores.

**Campos:**
- `supplier_id` (SERIAL, PRIMARY KEY): Identificador único del proveedor
- `supplier_name` (VARCHAR(255)): Nombre del proveedor

#### 7. Supplier_Product
Tabla intermedia que relaciona proveedores con productos, almacenando información de precios y cantidades.

**Campos:**
- `supplier_idP` (BIGINT, NOT NULL): Identificador del proveedor
- `product_idP` (BIGINT, NOT NULL): Identificador del producto
- `quantity` (INTEGER): Cantidad disponible del proveedor
- `unit_purchase_price` (INTEGER): Precio unitario de compra al proveedor

**Clave Primaria Compuesta:**
- PRIMARY KEY (`supplier_idP`, `product_idP`)

**Relaciones:**
- `fk_supplier_product`: Clave foránea que referencia a `Supplier(supplier_id)`
- `fk_product_supplier`: Clave foránea que referencia a `Products(id_product)`

---

## RELACIONES ENTRE TABLAS

### Diagrama de Relaciones

```
Stores (1) ────────< (N) Users
  │                   
  │                   
  ├─< (N) Inventory
  │       │
  │       └──> (1) Products
  │
  ├─< (N) Transactions (origen)
  │
  └─< (N) Transactions (destino)

Products (1) ──────< (N) Inventory
  │
  ├─< (N) Transactions
  │
  └─< (N) Supplier_Product ───> (N) Supplier
```

### Descripción Detallada de Relaciones

1. **Stores ↔ Users** (Uno a Muchos)
   - Una tienda puede tener múltiples usuarios
   - Un usuario pertenece a una tienda (o ninguna si es superadministrador)

2. **Stores ↔ Inventory** (Uno a Muchos)
   - Una tienda puede tener múltiples registros de inventario (uno por producto)
   - Cada registro de inventario pertenece a una única tienda

3. **Products ↔ Inventory** (Uno a Muchos)
   - Un producto puede estar en el inventario de múltiples tiendas
   - Cada registro de inventario corresponde a un único producto

4. **Products ↔ Transactions** (Uno a Muchos)
   - Un producto puede tener múltiples transacciones
   - Cada transacción involucra un único producto

5. **Stores ↔ Transactions** (Uno a Muchos - Origen)
   - Una tienda puede ser origen de múltiples transacciones
   - Cada transacción tiene una tienda origen

6. **Stores ↔ Transactions** (Uno a Muchos - Destino)
   - Una tienda puede ser destino de múltiples transacciones
   - Cada transacción tiene una tienda destino

7. **Products ↔ Supplier_Product** (Uno a Muchos)
   - Un producto puede ser ofrecido por múltiples proveedores
   - Cada relación producto-proveedor es única

8. **Supplier ↔ Supplier_Product** (Uno a Muchos)
   - Un proveedor puede ofrecer múltiples productos
   - Cada relación proveedor-producto es única

---

## TRIGGERS

### 1. trg_update_inventory

**Función:** `update_inventory_after_transaction()`

**Propósito:**
Actualiza automáticamente el inventario cuando se inserta una nueva transacción de tipo 'Sale' o 'Receipt'.
Este trigger garantiza la consistencia de datos al mantener el stock sincronizado con las transacciones.

**Funcionamiento:**

- **Para transacciones tipo 'Sale':**
  - Resta la cantidad de productos del inventario de la tienda origen
  - Verifica que haya stock suficiente antes de realizar la operación
  - Si no hay stock suficiente, lanza una excepción

- **Para transacciones tipo 'Receipt':**
  - Si el producto ya existe en el inventario de la tienda destino, suma la cantidad
  - Si el producto no existe en el inventario, crea un nuevo registro con la cantidad recibida

- **Para transacciones tipo 'Transfer':**
  - El trigger no procesa este tipo de transacción (se maneja mediante el procedimiento almacenado `transferir_inventario`)

**Cuándo se ejecuta:**
- Se activa automáticamente después de cada INSERT en la tabla `Transactions`
- Se ejecuta por cada fila insertada (FOR EACH ROW)

**Manejo de Errores:**
- Si ocurre un error durante la ejecución, toda la transacción se revierte automáticamente
- Lanza excepciones descriptivas en caso de stock insuficiente o errores en la actualización

---

## PROCEDIMIENTOS ALMACENADOS

### 1. transferir_inventario

**Parámetros:**
- `p_id_product` (BIGINT): Identificador del producto a transferir
- `p_id_store_origin` (BIGINT): Identificador de la tienda origen
- `p_id_store_destiny` (BIGINT): Identificador de la tienda destino
- `p_quantity` (INTEGER): Cantidad de productos a transferir

**Propósito:**
Procedimiento almacenado que gestiona la transferencia de inventario entre tiendas de forma transaccional,
garantizando la integridad de los datos y registrando la operación.

**Funcionamiento:**

1. **Validaciones:**
   - Verifica que exista stock suficiente en la tienda origen
   - Verifica que las tiendas origen y destino sean diferentes

2. **Actualización de Inventario:**
   - Resta la cantidad de la tienda origen
   - Si el producto existe en la tienda destino, suma la cantidad
   - Si el producto no existe en la tienda destino, crea un nuevo registro

3. **Registro de Transacción:**
   - Inserta un registro en la tabla `Transactions` con tipo 'Transfer'
   - Registra la fecha actual automáticamente

**Características:**
- Registro automático de la transacción

**Manejo de Errores:**
- Lanza excepciones descriptivas si:
  - No hay stock suficiente en la tienda origen
  - Las tiendas origen y destino son la misma

---

## VISTAS MATERIALIZADAS

### 1. resumen_stock_tienda

**Propósito:**
Almacena un resumen pre-calculado del valor total del inventario y la cantidad de productos únicos por tienda. Esta vista materializada mejora significativamente el rendimiento de consultas frecuentes que requieren información agregada del inventario.

**Campos:**
- `id_tienda` (BIGINT): Identificador de la tienda
- `nombre_tienda` (VARCHAR(255)): Nombre de la tienda
- `valor_total_inventario` (NUMERIC): Suma del valor total del inventario (stock × precio) para todos los productos de la tienda
- `productos_unicos` (INTEGER): Cantidad de productos diferentes en la tienda

**Ventajas:**
- **Rendimiento:** Evita recalcular agregaciones costosas en cada consulta
- **Optimización:** Reduce la carga en la base de datos para reportes frecuentes
- **Consistencia:** Almacena datos calculados que no cambian con frecuencia

**Actualización:**
- La vista debe actualizarse manualmente usando `REFRESH MATERIALIZED VIEW CONCURRENTLY resumen_stock_tienda`
- Se recomienda actualizar después de cambios significativos en el inventario o después de operaciones masivas

---

## ÍNDICES

### 1. idx_resumen_stock_tienda

**Tipo:** UNIQUE INDEX

**Ubicación:** Vista materializada `resumen_stock_tienda`

**Campos indexados:**
- `id_tienda`
- `nombre_tienda`

**Propósito:**
- **Unicidad:** Garantiza que no existan duplicados en la vista materializada
- **Rendimiento:** Acelera las búsquedas y consultas sobre la vista materializada
- **Optimización:** Facilita operaciones de actualización concurrente (`REFRESH MATERIALIZED VIEW CONCURRENTLY`)

**Nota:** Los índices UNIQUE en vistas materializadas son necesarios para permitir actualizaciones concurrentes sin bloquear la vista durante la actualización.

---

## NOTAS ADICIONALES

### Consideraciones de Rendimiento

1. **Vista Materializada:** Se debe actualizar periódicamente para mantener los datos actualizados. Se recomienda actualizarla después de operaciones que modifiquen significativamente el inventario.

2. **Transacciones:** El trigger y el procedimiento almacenado garantizan la integridad referencial y la consistencia de los datos mediante transacciones atómicas.

3. **Índices:** El índice único en la vista materializada es esencial para operaciones de actualización concurrente, permitiendo que la vista siga siendo consultable mientras se actualiza.

### Mantenimiento

- **Backup:** Se recomienda realizar backups regulares de todas las tablas
- **Logs:** Las transacciones quedan registradas en la tabla `Transactions` para auditoría
- **Monitoreo:** Se recomienda monitorear el tamaño de la vista materializada y la frecuencia de actualización
