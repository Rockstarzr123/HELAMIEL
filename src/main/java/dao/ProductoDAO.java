package dao;

import conexion.Conexion;
import modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Proporciona las operaciones CRUD de productos usando JDBC.
 */
public class ProductoDAO {

    private static final Logger LOGGER = Logger.getLogger(ProductoDAO.class.getName());

    private static final String SQL_INSERTAR = """
            INSERT INTO Productos (nombre, categoria, precio, stock, estado)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String SQL_LISTAR = """
            SELECT id_producto, nombre, categoria, precio, stock, estado
            FROM Productos
            ORDER BY id_producto DESC
            """;

    private static final String SQL_BUSCAR = """
            SELECT id_producto, nombre, categoria, precio, stock, estado
            FROM Productos
            WHERE id_producto = ?
            """;

    private static final String SQL_ACTUALIZAR = """
            UPDATE Productos
            SET nombre = ?, categoria = ?, precio = ?, stock = ?, estado = ?
            WHERE id_producto = ?
            """;

    private static final String SQL_ELIMINAR = """
            DELETE FROM Productos
            WHERE id_producto = ?
            """;

    private static final String SQL_EXISTE_NOMBRE_CATEGORIA = """
            SELECT COUNT(*) AS total
            FROM Productos
            WHERE LOWER(nombre) = LOWER(?)
            AND LOWER(categoria) = LOWER(?)
            """;

    private static final String SQL_EXISTE_NOMBRE_CATEGORIA_EXCLUYENDO_ID = """
            SELECT COUNT(*) AS total
            FROM Productos
            WHERE LOWER(nombre) = LOWER(?)
            AND LOWER(categoria) = LOWER(?)
            AND id_producto <> ?
            """;

    /**
     * Inserta un producto y asigna el id generado por MySQL al objeto recibido.
     *
     * @param producto producto que se va a registrar.
     * @return true si el producto fue insertado correctamente.
     */
    public boolean insertarProducto(Producto producto) {
        Objects.requireNonNull(producto, "El producto no puede ser nulo.");

        try (Connection conexion = Conexion.getConnection();
                PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {

            asignarParametrosProducto(sentencia, producto);

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                asignarIdGenerado(sentencia, producto);
                return true;
            }
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Error al insertar el producto.", exception);
        }

        return false;
    }

    /**
     * Lista todos los productos registrados.
     *
     * @return lista de productos encontrados.
     */
    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();

        try (Connection conexion = Conexion.getConnection();
                PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR);
                ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                productos.add(mapearProducto(resultado));
            }
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Error al listar los productos.", exception);
        }

        return productos;
    }

    /**
     * Busca un producto por su identificador.
     *
     * @param idProducto identificador del producto.
     * @return producto encontrado o un Optional vacio si no existe.
     */
    public Optional<Producto> buscarProducto(int idProducto) {
        try (Connection conexion = Conexion.getConnection();
                PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR)) {

            sentencia.setInt(1, idProducto);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(mapearProducto(resultado));
                }
            }
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Error al buscar el producto.", exception);
        }

        return Optional.empty();
    }

    /**
     * Verifica si ya existe un producto con el mismo nombre y categoria.
     *
     * @param nombre nombre del producto.
     * @param categoria categoria del producto.
     * @return true si existe un producto duplicado.
     */
    public boolean existeProductoPorNombreYCategoria(String nombre, String categoria) {
        return existeProductoPorNombreYCategoria(nombre, categoria, 0);
    }

    /**
     * Verifica duplicidad excluyendo un producto especifico, util para edicion.
     *
     * @param nombre nombre del producto.
     * @param categoria categoria del producto.
     * @param idProductoExcluir identificador que no se debe considerar duplicado.
     * @return true si existe otro producto con el mismo nombre y categoria.
     */
    public boolean existeProductoPorNombreYCategoria(String nombre, String categoria, int idProductoExcluir) {
        String consulta = idProductoExcluir > 0
                ? SQL_EXISTE_NOMBRE_CATEGORIA_EXCLUYENDO_ID
                : SQL_EXISTE_NOMBRE_CATEGORIA;

        try (Connection conexion = Conexion.getConnection();
                PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setString(1, nombre);
            sentencia.setString(2, categoria);

            if (idProductoExcluir > 0) {
                sentencia.setInt(3, idProductoExcluir);
            }

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt("total") > 0;
                }
            }
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Error al validar duplicidad del producto.", exception);
        }

        return false;
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param producto producto con los datos actualizados.
     * @return true si el producto fue actualizado correctamente.
     */
    public boolean actualizarProducto(Producto producto) {
        Objects.requireNonNull(producto, "El producto no puede ser nulo.");

        try (Connection conexion = Conexion.getConnection();
                PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR)) {

            asignarParametrosProducto(sentencia, producto);
            sentencia.setInt(6, producto.getIdProducto());

            return sentencia.executeUpdate() > 0;
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el producto.", exception);
        }

        return false;
    }

    /**
     * Elimina un producto por su identificador.
     *
     * @param idProducto identificador del producto.
     * @return true si el producto fue eliminado correctamente.
     */
    public boolean eliminarProducto(int idProducto) {
        try (Connection conexion = Conexion.getConnection();
                PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR)) {

            sentencia.setInt(1, idProducto);

            return sentencia.executeUpdate() > 0;
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el producto.", exception);
        }

        return false;
    }

    /**
     * Asigna los valores comunes usados por las sentencias de insercion y actualizacion.
     *
     * @param sentencia sentencia preparada que recibira los parametros.
     * @param producto producto con los datos que se van a persistir.
     * @throws SQLException si ocurre un error al asignar parametros.
     */
    private void asignarParametrosProducto(PreparedStatement sentencia, Producto producto) throws SQLException {
        sentencia.setString(1, producto.getNombre());
        sentencia.setString(2, producto.getCategoria());
        sentencia.setBigDecimal(3, producto.getPrecio());
        sentencia.setInt(4, producto.getStock());
        sentencia.setBoolean(5, producto.isEstado());
    }

    /**
     * Asigna al producto el identificador generado automaticamente por MySQL.
     *
     * @param sentencia sentencia que ejecuto la insercion.
     * @param producto producto insertado.
     * @throws SQLException si ocurre un error al leer la clave generada.
     */
    private void asignarIdGenerado(PreparedStatement sentencia, Producto producto) throws SQLException {
        try (ResultSet clavesGeneradas = sentencia.getGeneratedKeys()) {
            if (clavesGeneradas.next()) {
                producto.setIdProducto(clavesGeneradas.getInt(1));
            }
        }
    }

    /**
     * Convierte una fila del resultado SQL en un objeto Producto.
     *
     * @param resultado fila actual del resultado SQL.
     * @return producto construido con los datos de la fila.
     * @throws SQLException si ocurre un error al leer los datos.
     */
    private Producto mapearProducto(ResultSet resultado) throws SQLException {
        Producto producto = new Producto();

        producto.setIdProducto(resultado.getInt("id_producto"));
        producto.setNombre(resultado.getString("nombre"));
        producto.setCategoria(resultado.getString("categoria"));
        producto.setPrecio(resultado.getBigDecimal("precio"));
        producto.setStock(resultado.getInt("stock"));
        producto.setEstado(resultado.getBoolean("estado"));

        return producto;
    }
}
