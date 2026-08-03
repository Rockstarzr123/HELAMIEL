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

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private static final String VISTA_LISTADO = "productos/listar";
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

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

    @PostMapping("/editar/{idProducto}")
    public String editar(
            @PathVariable Long idProducto,
            @Valid @ModelAttribute("productoDTO") ProductoDTO productoDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            productoDTO.setId(idProducto); // Ajustado a setId en lugar de setIdProducto
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

    @PostMapping("/eliminar/{idProducto}")
    public String eliminar(@PathVariable int idProducto, RedirectAttributes redirectAttributes) {
        Producto producto = productoService.buscarProducto(idProducto);
        productoService.eliminarProducto(idProducto);

        redirectAttributes.addFlashAttribute("mensajeExito",
                "Producto \"" + producto.getNombre() + "\" eliminado correctamente.");

        return "redirect:/productos";
    }
}