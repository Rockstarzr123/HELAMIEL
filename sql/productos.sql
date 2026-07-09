-- ============================================================
-- HELAMIEL — Script de base de datos del módulo de Productos
-- Proyecto formativo SENA | GA7-220501096-AA4-EV03
-- ============================================================
-- Este script es idempotente y compatible con cualquier version
-- de MySQL 8.x (no depende de "ADD COLUMN IF NOT EXISTS" ni de
-- "CREATE INDEX IF NOT EXISTS", que solo existen desde 8.0.29).
-- Puede ejecutarse varias veces sin duplicar la tabla ni fallar
-- si la tabla "Productos" ya existia (por ejemplo, creada antes
-- por el DAO JDBC).
-- ============================================================

CREATE DATABASE IF NOT EXISTS helamiel
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE helamiel;

CREATE TABLE IF NOT EXISTS Productos (
    id_producto     INT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(100)   NOT NULL,
    descripcion     VARCHAR(255)   NULL,
    categoria       VARCHAR(60)    NOT NULL,
    precio          DECIMAL(10,2)  NOT NULL,
    stock           INT            NOT NULL DEFAULT 0,
    estado          TINYINT(1)     NOT NULL DEFAULT 1,
    fecha_registro  DATETIME       NULL,
    CONSTRAINT chk_productos_precio CHECK (precio > 0),
    CONSTRAINT chk_productos_stock  CHECK (stock >= 0)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- ------------------------------------------------------------
-- Migracion segura de columnas (por si la tabla ya existia sin
-- "descripcion" / "fecha_registro", creada por el DAO JDBC).
-- Se usa un procedimiento temporal que verifica information_schema
-- antes de alterar, ya que MySQL < 8.0.29 no soporta
-- "ADD COLUMN IF NOT EXISTS".
-- ------------------------------------------------------------
DROP PROCEDURE IF EXISTS agregar_columna_si_no_existe;

DELIMITER $$
CREATE PROCEDURE agregar_columna_si_no_existe()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.COLUMNS
        WHERE TABLE_SCHEMA = 'helamiel' AND TABLE_NAME = 'Productos' AND COLUMN_NAME = 'descripcion'
    ) THEN
        ALTER TABLE Productos ADD COLUMN descripcion VARCHAR(255) NULL AFTER nombre;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.COLUMNS
        WHERE TABLE_SCHEMA = 'helamiel' AND TABLE_NAME = 'Productos' AND COLUMN_NAME = 'fecha_registro'
    ) THEN
        ALTER TABLE Productos ADD COLUMN fecha_registro DATETIME NULL;
    END IF;
END$$
DELIMITER ;

CALL agregar_columna_si_no_existe();
DROP PROCEDURE agregar_columna_si_no_existe;

-- ------------------------------------------------------------
-- Indices para acelerar busquedas y filtros del listado
-- (se valida su existencia por la misma razon anterior)
-- ------------------------------------------------------------
SET @indice_categoria := (
    SELECT COUNT(1) FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = 'helamiel' AND TABLE_NAME = 'Productos' AND INDEX_NAME = 'idx_productos_categoria'
);
SET @sql_categoria := IF(@indice_categoria = 0,
    'CREATE INDEX idx_productos_categoria ON Productos (categoria)',
    'SELECT 1');
PREPARE stmt_categoria FROM @sql_categoria;
EXECUTE stmt_categoria;
DEALLOCATE PREPARE stmt_categoria;

SET @indice_estado := (
    SELECT COUNT(1) FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = 'helamiel' AND TABLE_NAME = 'Productos' AND INDEX_NAME = 'idx_productos_estado'
);
SET @sql_estado := IF(@indice_estado = 0,
    'CREATE INDEX idx_productos_estado ON Productos (estado)',
    'SELECT 1');
PREPARE stmt_estado FROM @sql_estado;
EXECUTE stmt_estado;
DEALLOCATE PREPARE stmt_estado;

-- Datos de ejemplo opcionales (comentar si no se desean)
-- INSERT INTO Productos (nombre, descripcion, categoria, precio, stock, estado, fecha_registro) VALUES
-- ('Helado de Fresa Artesanal', 'Helado cremoso elaborado con fresas naturales', 'Helados', 8500.00, 40, 1, NOW()),
-- ('Helado de Vainilla', 'Receta clásica de vainilla de Madagascar', 'Helados', 7500.00, 55, 1, NOW()),
-- ('Topping de Chocolate', 'Salsa de chocolate belga para decorar', 'Toppings', 3200.00, 100, 1, NOW()),
-- ('Cono Waffle', 'Cono artesanal crocante', 'Complementos', 1200.00, 200, 1, NOW());
