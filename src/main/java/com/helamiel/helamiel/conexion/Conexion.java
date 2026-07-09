package com.helamiel.helamiel.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Administra la conexion JDBC con la base de datos MySQL del proyecto.
 */
public final class Conexion {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = System.getenv().getOrDefault(
            "DB_URL", "jdbc:mysql://localhost:3306/helamiel?useUnicode=true&characterEncoding=UTF-8&serverTimezone=America/Bogota");
    private static final String USUARIO = System.getenv().getOrDefault("DB_USER", "root");
    private static final String CONTRASENA = System.getenv().getOrDefault("DB_PASSWORD", "");

    private Conexion() {
    }

    /**
     * Obtiene una conexion activa con la base de datos HELAMIEL.
     *
     * @return conexion JDBC lista para usar.
     * @throws SQLException si el driver no esta disponible o la conexion falla.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException exception) {
            throw new SQLException("No se encontro el driver de MySQL.", exception);
        }
    }
}
