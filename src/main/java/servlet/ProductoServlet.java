package servlet;

import dao.ProductoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Producto;
import util.ProductoValidador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador web del modulo de productos.
 */
@WebServlet(name = "ProductoServlet", urlPatterns = {"/productos"})
public class ProductoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String VISTA_PRODUCTOS = "/WEB-INF/views/productos.jsp";
    private static final String VISTA_NUEVO = "/WEB-INF/views/nuevoProducto.jsp";
    private static final String VISTA_EDITAR = "/WEB-INF/views/editarProducto.jsp";
    private static final String VISTA_CONFIRMAR_ELIMINAR = "/WEB-INF/views/confirmarEliminarProducto.jsp";
    private static final String VISTA_MENSAJE = "/WEB-INF/views/mensaje.jsp";

    private final ProductoDAO productoDao = new ProductoDAO();

    /**
     * GET se usa para consultar informacion, abrir formularios y mostrar confirmaciones.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        transferirMensajesSesion(request);

        String accion = obtenerAccion(request);

        switch (accion) {
            case "nuevo" -> mostrarFormularioNuevo(request, response);
            case "editar" -> mostrarFormularioEditar(request, response);
            case "eliminar" -> mostrarConfirmacionEliminar(request, response);
            case "buscar" -> buscarProducto(request, response);
            default -> listarProductos(request, response);
        }
    }

    /**
     * POST se usa para procesar formularios que crean, modifican o eliminan datos.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = obtenerAccion(request);

        switch (accion) {
            case "registrar" -> registrarProducto(request, response);
            case "actualizar" -> actualizarProducto(request, response);
            case "eliminarConfirmado" -> eliminarProducto(request, response);
            default -> redirigirListado(request, response);
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("productos", productoDao.listarProductos());
        reenviar(request, response, VISTA_PRODUCTOS);
    }

    private void buscarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProducto = ProductoValidador.obtenerIdValido(request.getParameter("id"));

        if (idProducto == 0) {
            request.setAttribute("mensajeError", "Ingrese un ID de producto valido para realizar la busqueda.");
            listarProductos(request, response);
            return;
        }

        Optional<Producto> productoEncontrado = productoDao.buscarProducto(idProducto);

        if (productoEncontrado.isPresent()) {
            request.setAttribute("productos", List.of(productoEncontrado.get()));
            request.setAttribute("mensajeExito", "Producto encontrado correctamente.");
        } else {
            request.setAttribute("productos", new ArrayList<Producto>());
            request.setAttribute("mensajeError", "No se encontro un producto con el ID ingresado.");
        }

        request.setAttribute("idBusqueda", idProducto);
        reenviar(request, response, VISTA_PRODUCTOS);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getAttribute("producto") == null) {
            Producto producto = new Producto();
            producto.setEstado(true);
            request.setAttribute("producto", producto);
        }

        reenviar(request, response, VISTA_NUEVO);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProducto = ProductoValidador.obtenerIdValido(request.getParameter("id"));

        if (idProducto == 0) {
            mostrarMensaje(request, response, "ID de producto no valido.", "danger");
            return;
        }

        Optional<Producto> producto = productoDao.buscarProducto(idProducto);

        if (producto.isEmpty()) {
            mostrarMensaje(request, response, "No se encontro el producto solicitado para editar.", "warning");
            return;
        }

        request.setAttribute("producto", producto.get());
        reenviar(request, response, VISTA_EDITAR);
    }

    private void mostrarConfirmacionEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProducto = ProductoValidador.obtenerIdValido(request.getParameter("id"));

        if (idProducto == 0) {
            mostrarMensaje(request, response, "ID de producto no valido.", "danger");
            return;
        }

        Optional<Producto> producto = productoDao.buscarProducto(idProducto);

        if (producto.isEmpty()) {
            mostrarMensaje(request, response, "No se encontro el producto solicitado para eliminar.", "warning");
            return;
        }

        request.setAttribute("producto", producto.get());
        reenviar(request, response, VISTA_CONFIRMAR_ELIMINAR);
    }

    private void registrarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<String> errores = validarFormulario(request, 0);
        Producto producto = crearProductoDesdeRequest(request, 0);

        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("producto", producto);
            mostrarFormularioNuevo(request, response);
            return;
        }

        if (productoDao.insertarProducto(producto)) {
            guardarMensajeSesion(request, "Producto registrado correctamente.", "success");
        } else {
            guardarMensajeSesion(request, "No se pudo registrar el producto. Revise la conexion con la base de datos.", "danger");
        }

        redirigirListado(request, response);
    }

    private void actualizarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProducto = ProductoValidador.obtenerIdValido(request.getParameter("idProducto"));

        if (idProducto == 0) {
            mostrarMensaje(request, response, "ID de producto no valido para actualizar.", "danger");
            return;
        }

        List<String> errores = validarFormulario(request, idProducto);
        Producto producto = crearProductoDesdeRequest(request, idProducto);

        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("producto", producto);
            reenviar(request, response, VISTA_EDITAR);
            return;
        }

        if (productoDao.actualizarProducto(producto)) {
            guardarMensajeSesion(request, "Producto actualizado correctamente.", "success");
        } else {
            guardarMensajeSesion(request, "No se pudo actualizar el producto solicitado.", "danger");
        }

        redirigirListado(request, response);
    }

    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int idProducto = ProductoValidador.obtenerIdValido(request.getParameter("idProducto"));

        if (idProducto == 0) {
            guardarMensajeSesion(request, "ID de producto no valido para eliminar.", "danger");
            redirigirListado(request, response);
            return;
        }

        if (productoDao.eliminarProducto(idProducto)) {
            guardarMensajeSesion(request, "Producto eliminado correctamente.", "success");
        } else {
            guardarMensajeSesion(request, "No se pudo eliminar el producto solicitado.", "danger");
        }

        redirigirListado(request, response);
    }

    private List<String> validarFormulario(HttpServletRequest request, int idProductoExcluir) {
        String nombre = request.getParameter("nombre");
        String categoria = request.getParameter("categoria");
        String precio = request.getParameter("precio");
        String stock = request.getParameter("stock");
        String estado = request.getParameter("estado");

        List<String> errores = ProductoValidador.validar(nombre, categoria, precio, stock, estado);

        if (errores.isEmpty()) {
            String nombreNormalizado = ProductoValidador.normalizar(nombre);
            String categoriaNormalizada = ProductoValidador.normalizar(categoria);

            if (idProductoExcluir > 0
                    ? productoDao.existeProductoPorNombreYCategoria(nombreNormalizado, categoriaNormalizada, idProductoExcluir)
                    : productoDao.existeProductoPorNombreYCategoria(nombreNormalizado, categoriaNormalizada)) {
                errores.add("Ya existe un producto registrado con el mismo nombre y categoria.");
            }
        }

        return errores;
    }

    private Producto crearProductoDesdeRequest(HttpServletRequest request, int idProducto) {
        try {
            return ProductoValidador.construirProducto(
                    idProducto,
                    request.getParameter("nombre"),
                    request.getParameter("categoria"),
                    request.getParameter("precio"),
                    request.getParameter("stock"),
                    request.getParameter("estado")
            );
        } catch (NumberFormatException exception) {
            Producto producto = new Producto();
            producto.setIdProducto(idProducto);
            producto.setNombre(ProductoValidador.normalizar(request.getParameter("nombre")));
            producto.setCategoria(ProductoValidador.normalizar(request.getParameter("categoria")));
            producto.setEstado(Boolean.parseBoolean(ProductoValidador.normalizar(request.getParameter("estado"))));
            return producto;
        }
    }

    private String obtenerAccion(HttpServletRequest request) {
        String accion = request.getParameter("accion");
        return accion == null || accion.isBlank() ? "listar" : accion;
    }

    private void mostrarMensaje(HttpServletRequest request, HttpServletResponse response,
            String mensaje, String tipo) throws ServletException, IOException {

        request.setAttribute("mensaje", mensaje);
        request.setAttribute("tipoMensaje", tipo);
        reenviar(request, response, VISTA_MENSAJE);
    }

    private void guardarMensajeSesion(HttpServletRequest request, String mensaje, String tipo) {
        HttpSession sesion = request.getSession();
        sesion.setAttribute("mensajeFlash", mensaje);
        sesion.setAttribute("tipoMensajeFlash", tipo);
    }

    private void transferirMensajesSesion(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);

        if (sesion == null) {
            return;
        }

        Object mensaje = sesion.getAttribute("mensajeFlash");
        Object tipo = sesion.getAttribute("tipoMensajeFlash");

        if (mensaje != null) {
            request.setAttribute("mensaje", mensaje);
            request.setAttribute("tipoMensaje", tipo);
            sesion.removeAttribute("mensajeFlash");
            sesion.removeAttribute("tipoMensajeFlash");
        }
    }

    private void redirigirListado(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.sendRedirect(request.getContextPath() + "/productos");
    }

    private void reenviar(HttpServletRequest request, HttpServletResponse response, String vista)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher(vista);
        dispatcher.forward(request, response);
    }
}
