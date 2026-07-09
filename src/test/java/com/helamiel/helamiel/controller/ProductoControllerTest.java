package com.helamiel.helamiel.controller;

import com.helamiel.helamiel.dto.ProductoDTO;
import com.helamiel.helamiel.model.Producto;
import com.helamiel.helamiel.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoControllerTest {

    @Test
    void listarShouldPopulateModelAndReturnListView() {
        ProductoService service = mock(ProductoService.class);
        ProductoController controller = new ProductoController(service);
        Model model = new ConcurrentModel();
        List<Producto> productos = List.of(new Producto(1, "Helado", "Helados", new BigDecimal("5.0"), 3, true));

        when(service.listarProductos()).thenReturn(productos);
        when(service.listarCategorias()).thenReturn(List.of("Helados"));

        String vista = controller.listar(null, null, null, model);

        assertEquals("productos/listar", vista);
        assertSame(productos, model.getAttribute("productos"));
        assertTrue(model.getAttribute("productoDTO") instanceof ProductoDTO);
        verify(service, times(1)).listarProductos();
    }

    @Test
    void guardarShouldRedirectAndCallServiceWhenValid() {
        ProductoService service = mock(ProductoService.class);
        ProductoController controller = new ProductoController(service);
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Helado de Coco");
        dto.setCategoria("Helados");
        dto.setPrecio(new BigDecimal("7000"));
        dto.setStock(10);

        BindingResult bindingResult = new BeanPropertyBindingResult(dto, "productoDTO");
        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

        String vista = controller.guardar(dto, bindingResult, redirectAttributes);

        assertEquals("redirect:/productos", vista);
        verify(service, times(1)).guardarProducto(dto);
        assertNotNull(redirectAttributes.getFlashAttributes().get("mensajeExito"));
    }

    @Test
    void guardarShouldNotCallServiceWhenBindingHasErrors() {
        ProductoService service = mock(ProductoService.class);
        ProductoController controller = new ProductoController(service);
        ProductoDTO dto = new ProductoDTO();

        BindingResult bindingResult = new BeanPropertyBindingResult(dto, "productoDTO");
        bindingResult.reject("nombre", "El nombre es obligatorio");
        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

        String vista = controller.guardar(dto, bindingResult, redirectAttributes);

        assertEquals("redirect:/productos", vista);
        verify(service, never()).guardarProducto(any());
    }

    @Test
    void eliminarShouldCallServiceAndRedirect() {
        ProductoService service = mock(ProductoService.class);
        ProductoController controller = new ProductoController(service);
        Producto producto = new Producto(4, "Helado de Limón", "Helados", new BigDecimal("6000"), 8, true);
        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

        when(service.buscarProducto(4)).thenReturn(producto);

        String vista = controller.eliminar(4, redirectAttributes);

        assertEquals("redirect:/productos", vista);
        verify(service, times(1)).eliminarProducto(4);
    }
}
