package com.helamiel.helamiel.controller;

import com.helamiel.helamiel.dto.ProductoDTO;
import com.helamiel.helamiel.model.Producto;
import com.helamiel.helamiel.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controlador MVC del modulo de Gestion de Productos.
 *
 * <p>Expone las operaciones de registro, consulta, edicion, eliminacion,
 * busqueda y filtrado de productos, renderizando las vistas Thymeleaf del
 * panel administrativo de HELAMIEL.</p>
 */
@Controller
@RequestMapping("/productos")
public class ProductoController {

    private static final String VISTA_LISTADO = "productos/listar";

    private final ProductoService productoService;

    /**
     * Crea el controlador inyectando el servicio de productos.
     *
     * @param productoService servicio con la logica de negocio de productos.
     */
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Muestra el listado de productos, aplicando busqueda y filtros
     * opcionales enviados por la interfaz.
     *
     * @param texto texto de busqueda libre (opcional).
     * @param categoria categoria para filtrar (opcional).
     * @param estado estado para filtrar: true=activo, false=inactivo (opcional).
     * @param model modelo que alimenta la vista.
     * @return nombre de la vista del listado de productos.
     */
    @GetMapping
    public String listar(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Boolean estado,
            Model model) {

        List<Producto> productos;

        if (texto != null && !texto.isBlank()) {
            productos = productoService.buscarPorTexto(texto);
        } else if ((categoria != null && !categoria.isBlank()) || estado != null) {
            productos = productoService.filtrarProductos(categoria, estado);
        } else {
            productos = productoService.listarProductos();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", productoService.listarCategorias());
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("texto", texto);
        model.addAttribute("categoriaSeleccionada", categoria);
        model.addAttribute("estadoSeleccionado", estado);

        if (!model.containsAttribute("productoDTO")) {
            model.addAttribute("productoDTO", new ProductoDTO());
        }

        return VISTA_LISTADO;
    }

    /**
     * Registra un nuevo producto enviado desde el formulario modal.
     *
     * @param productoDTO datos capturados en el formulario.
     * @param bindingResult resultado de las validaciones del formulario.
     * @param redirectAttributes atributos flash para mensajes al usuario.
     * @return redireccion al listado de productos.
     */
    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("productoDTO") ProductoDTO productoDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productoDTO",
                    bindingResult);
            redirectAttributes.addFlashAttribute("productoDTO", productoDTO);
            redirectAttributes.addFlashAttribute("abrirModal", "registrar");
            redirectAttributes.addFlashAttribute("mensajeError", "Revisa los datos del formulario.");
            return "redirect:/productos";
        }

        productoService.guardarProducto(productoDTO);
        redirectAttributes.addFlashAttribute("mensajeExito",
                "Producto \"" + productoDTO.getNombre() + "\" registrado correctamente.");

        return "redirect:/productos";
    }

    /**
     * Actualiza un producto existente enviado desde el formulario modal de
     * edicion.
     *
     * @param idProducto identificador del producto a editar.
     * @param productoDTO datos actualizados capturados en el formulario.
     * @param bindingResult resultado de las validaciones del formulario.
     * @param redirectAttributes atributos flash para mensajes al usuario.
     * @return redireccion al listado de productos.
     */
    @PostMapping("/editar/{idProducto}")
    public String editar(
            @PathVariable int idProducto,
            @Valid @ModelAttribute("productoDTO") ProductoDTO productoDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            productoDTO.setIdProducto(idProducto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productoDTO",
                    bindingResult);
            redirectAttributes.addFlashAttribute("productoDTO", productoDTO);
            redirectAttributes.addFlashAttribute("abrirModal", "editar-" + idProducto);
            redirectAttributes.addFlashAttribute("mensajeError", "Revisa los datos del formulario.");
            return "redirect:/productos";
        }

        productoService.editarProducto(idProducto, productoDTO);
        redirectAttributes.addFlashAttribute("mensajeExito",
                "Producto \"" + productoDTO.getNombre() + "\" actualizado correctamente.");

        return "redirect:/productos";
    }

    /**
     * Elimina un producto por su identificador.
     *
     * @param idProducto identificador del producto a eliminar.
     * @param redirectAttributes atributos flash para mensajes al usuario.
     * @return redireccion al listado de productos.
     */
    @PostMapping("/eliminar/{idProducto}")
    public String eliminar(@PathVariable int idProducto, RedirectAttributes redirectAttributes) {
        Producto producto = productoService.buscarProducto(idProducto);
        productoService.eliminarProducto(idProducto);

        redirectAttributes.addFlashAttribute("mensajeExito",
                "Producto \"" + producto.getNombre() + "\" eliminado correctamente.");

        return "redirect:/productos";
    }
}
