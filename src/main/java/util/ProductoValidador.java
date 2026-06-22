package util;

import modelo.Producto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Centraliza las validaciones de servidor para el modulo web de productos.
 */
public final class ProductoValidador {

    private static final int LONGITUD_MINIMA_TEXTO = 3;
    private static final int LONGITUD_MAXIMA_NOMBRE = 100;
    private static final int LONGITUD_MAXIMA_CATEGORIA = 50;
    private static final Pattern TEXTO_PERMITIDO = Pattern.compile("^[\\p{L}0-9 .,'-]+$");

    private ProductoValidador() {
    }

    /**
     * Valida los datos recibidos desde los formularios JSP antes de usar el DAO.
     *
     * @param nombre nombre recibido.
     * @param categoria categoria recibida.
     * @param precioTexto precio recibido como texto.
     * @param stockTexto stock recibido como texto.
     * @param estadoTexto estado recibido como texto.
     * @return lista de errores encontrados; vacia si los datos son validos.
     */
    public static List<String> validar(String nombre, String categoria, String precioTexto,
            String stockTexto, String estadoTexto) {

        List<String> errores = new ArrayList<>();

        validarTexto("nombre", normalizar(nombre), LONGITUD_MAXIMA_NOMBRE, errores);
        validarTexto("categoria", normalizar(categoria), LONGITUD_MAXIMA_CATEGORIA, errores);
        validarPrecio(precioTexto, errores);
        validarStock(stockTexto, errores);
        validarEstado(estadoTexto, errores);

        return errores;
    }

    /**
     * Construye un producto con datos normalizados despues de validar el formulario.
     *
     * @param idProducto identificador del producto; cero para registros nuevos.
     * @param nombre nombre recibido.
     * @param categoria categoria recibida.
     * @param precioTexto precio recibido como texto.
     * @param stockTexto stock recibido como texto.
     * @param estadoTexto estado recibido como texto.
     * @return producto listo para persistir.
     */
    public static Producto construirProducto(int idProducto, String nombre, String categoria,
            String precioTexto, String stockTexto, String estadoTexto) {

        return new Producto(
                idProducto,
                normalizar(nombre),
                normalizar(categoria),
                new BigDecimal(normalizar(precioTexto)),
                Integer.parseInt(normalizar(stockTexto)),
                Boolean.parseBoolean(normalizar(estadoTexto))
        );
    }

    /**
     * Recorta espacios externos y compacta espacios internos multiples.
     *
     * @param valor texto recibido.
     * @return texto normalizado.
     */
    public static String normalizar(String valor) {
        if (valor == null) {
            return "";
        }

        return valor.trim().replaceAll("\\s+", " ");
    }

    /**
     * Convierte un parametro id en entero positivo.
     *
     * @param idTexto id recibido en la URL.
     * @return id numerico o cero cuando no es valido.
     */
    public static int obtenerIdValido(String idTexto) {
        try {
            int id = Integer.parseInt(normalizar(idTexto));
            return id > 0 ? id : 0;
        } catch (NumberFormatException exception) {
            return 0;
        }
    }

    private static void validarTexto(String campo, String valor, int longitudMaxima, List<String> errores) {
        if (valor.isBlank()) {
            errores.add("El campo " + campo + " es obligatorio.");
            return;
        }

        if (valor.length() < LONGITUD_MINIMA_TEXTO) {
            errores.add("El campo " + campo + " debe tener minimo "
                    + LONGITUD_MINIMA_TEXTO + " caracteres.");
        }

        if (valor.length() > longitudMaxima) {
            errores.add("El campo " + campo + " no debe superar " + longitudMaxima + " caracteres.");
        }

        if (!TEXTO_PERMITIDO.matcher(valor).matches()) {
            errores.add("El campo " + campo + " contiene caracteres no permitidos.");
        }
    }

    private static void validarPrecio(String precioTexto, List<String> errores) {
        String valor = normalizar(precioTexto);

        if (valor.isBlank()) {
            errores.add("El precio es obligatorio.");
            return;
        }

        try {
            BigDecimal precio = new BigDecimal(valor);
            if (precio.compareTo(BigDecimal.ZERO) <= 0) {
                errores.add("El precio debe ser mayor que cero.");
            }
        } catch (NumberFormatException exception) {
            errores.add("El precio debe ser un numero decimal valido.");
        }
    }

    private static void validarStock(String stockTexto, List<String> errores) {
        String valor = normalizar(stockTexto);

        if (valor.isBlank()) {
            errores.add("El stock es obligatorio.");
            return;
        }

        try {
            int stock = Integer.parseInt(valor);
            if (stock <= 0) {
                errores.add("El stock debe ser mayor que cero.");
            }
        } catch (NumberFormatException exception) {
            errores.add("El stock debe ser un numero entero valido.");
        }
    }

    private static void validarEstado(String estadoTexto, List<String> errores) {
        String valor = normalizar(estadoTexto);

        if (!"true".equalsIgnoreCase(valor) && !"false".equalsIgnoreCase(valor)) {
            errores.add("El estado debe ser Activo o Inactivo.");
        }
    }
}
