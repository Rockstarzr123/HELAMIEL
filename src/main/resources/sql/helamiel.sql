CREATE DATABASE IF NOT EXISTS helamiel;

USE helamiel;

CREATE TABLE IF NOT EXISTS Productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    estado BOOLEAN NOT NULL
);

INSERT INTO Productos (nombre, categoria, precio, stock, estado) VALUES
('Helado de vainilla', 'Helados', 4500.00, 30, TRUE),
('Helado de chocolate', 'Helados', 4800.00, 25, TRUE),
('Paleta de fresa', 'Paletas', 3000.00, 40, TRUE),
('Cono familiar', 'Conos', 6500.00, 15, TRUE),
('Malteada de mora', 'Bebidas', 7000.00, 12, TRUE);
