package vista;

import dao.ProductoDAO;
import modelo.Producto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Presenta el menu principal de consola e invoca las operaciones del DAO.
 */
public class Menu {

    private static final int OPCION_INSERTAR = 1;
    private static final int OPCION_CONSULTAR = 2;
    private static final int OPCION_ACTUALIZAR = 3;
    private static final int OPCION_ELIMINAR = 4;
    private static final int OPCION_SALIR = 5;

    private final Scanner scanner;
    private final ProductoDAO productoDao;

    /**
     * Inicializa las dependencias necesarias para el menu de consola.
     */
    public Menu() {
        scanner = new Scanner(System.in);
        productoDao = new ProductoDAO();
    }

    /**
     * Inicia el ciclo del menu principal.
     */
    public void iniciar() {
        int opcion;

        do {
            mostrarOpciones();
            opcion = leerEntero("Seleccione una opcion: ");
            procesarOpcion(opcion);
        } while (opcion != OPCION_SALIR);

        scanner.close();
    }

    /**
     * Muestra las opciones disponibles del menu principal.
     */
    private void mostrarOpciones() {
        System.out.println();
        System.out.println("===== MENU HELAMIEL =====");
        System.out.println("1 Insertar");
        System.out.println("2 Consultar");
        System.out.println("3 Actualizar");
        System.out.println("4 Eliminar");
        System.out.println("5 Salir");
    }

    /**
     * Ejecuta la accion correspondiente a la opcion seleccionada.
     *
     * @param opcion opcion ingresada por el usuario.
     */
    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case OPCION_INSERTAR -> insertar();
            case OPCION_CONSULTAR -> consultar();
            case OPCION_ACTUALIZAR -> actualizar();
            case OPCION_ELIMINAR -> eliminar();
            case OPCION_SALIR -> System.out.println("Saliendo del sistema...");
            default -> System.out.println("Opcion no valida.");
        }
    }

    /**
     * Solicita los datos de un producto y lo registra en la base de datos.
     */
    private void insertar() {
        System.out.println();
        System.out.println("Registrar producto");

        Producto producto = leerDatosProducto();
        boolean productoInsertado = productoDao.insertarProducto(producto);

        mostrarResultado(
                productoInsertado,
                "Producto insertado correctamente.",
                "No se pudo insertar el producto."
        );
    }

    /**
     * Consulta y muestra todos los productos registrados.
     */
    private void consultar() {
        System.out.println();
        System.out.println("Listado de productos");

        List<Producto> productos = productoDao.listarProductos();

        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        productos.forEach(System.out::println);
    }

    /**
     * Actualiza un producto existente a partir de su identificador.
     */
    private void actualizar() {
        System.out.println();
        System.out.println("Actualizar producto");

        int idProducto = leerEntero("Ingrese el id del producto: ");
        Optional<Producto> productoExistente = productoDao.buscarProducto(idProducto);

        if (productoExistente.isEmpty()) {
            System.out.println("No se encontro un producto con ese id.");
            return;
        }

        Producto productoActualizado = leerDatosProducto();
        productoActualizado.setIdProducto(idProducto);

        boolean productoActualizadoCorrectamente = productoDao.actualizarProducto(productoActualizado);

        mostrarResultado(
                productoActualizadoCorrectamente,
                "Producto actualizado correctamente.",
                "No se pudo actualizar el producto."
        );
    }

    /**
     * Elimina un producto a partir de su identificador.
     */
    private void eliminar() {
        System.out.println();
        System.out.println("Eliminar producto");

        int idProducto = leerEntero("Ingrese el id del producto: ");
        boolean productoEliminado = productoDao.eliminarProducto(idProducto);

        mostrarResultado(
                productoEliminado,
                "Producto eliminado correctamente.",
                "No se pudo eliminar el producto."
        );
    }

    /**
     * Lee desde consola los datos necesarios para crear o actualizar un producto.
     *
     * @return producto construido con la informacion ingresada.
     */
    private Producto leerDatosProducto() {
        String nombre = leerTexto("Nombre: ");
        String categoria = leerTexto("Categoria: ");
        BigDecimal precio = leerPrecio("Precio: ");
        int stock = leerEntero("Stock: ");
        boolean estado = leerBooleano("Estado activo (true/false): ");

        return new Producto(0, nombre, categoria, precio, stock, estado);
    }

    /**
     * Lee una cadena de texto desde consola.
     *
     * @param mensaje mensaje mostrado al usuario.
     * @return texto ingresado sin espacios externos.
     */
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    /**
     * Lee un numero entero desde consola y repite la solicitud si el dato no es valido.
     *
     * @param mensaje mensaje mostrado al usuario.
     * @return numero entero ingresado.
     */
    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim();

            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException exception) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    /**
     * Lee un precio desde consola usando BigDecimal para conservar precision.
     *
     * @param mensaje mensaje mostrado al usuario.
     * @return precio ingresado.
     */
    private BigDecimal leerPrecio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim();

            try {
                return new BigDecimal(valor);
            } catch (NumberFormatException exception) {
                System.out.println("Ingrese un numero decimal valido.");
            }
        }
    }

    /**
     * Lee un valor booleano desde consola.
     *
     * @param mensaje mensaje mostrado al usuario.
     * @return true o false segun el valor ingresado.
     */
    private boolean leerBooleano(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim();

            if ("true".equalsIgnoreCase(valor) || "false".equalsIgnoreCase(valor)) {
                return Boolean.parseBoolean(valor);
            }

            System.out.println("Ingrese true o false.");
        }
    }

    /**
     * Muestra un mensaje segun el resultado de una operacion.
     *
     * @param operacionExitosa indica si la operacion fue exitosa.
     * @param mensajeExitoso mensaje mostrado cuando la operacion es exitosa.
     * @param mensajeError mensaje mostrado cuando la operacion falla.
     */
    private void mostrarResultado(boolean operacionExitosa, String mensajeExitoso, String mensajeError) {
        if (operacionExitosa) {
            System.out.println(mensajeExitoso);
        } else {
            System.out.println(mensajeError);
        }
    }
}
