package com.helamiel.helamiel.service;

import com.helamiel.helamiel.dto.ProductoDTO;
import com.helamiel.helamiel.exception.ProductoDuplicadoException;
import com.helamiel.helamiel.exception.ProductoNoEncontradoException;
import com.helamiel.helamiel.model.Producto;
import com.helamiel.helamiel.repository.ProductoRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("null")
class ProductoServiceTest {

    private ProductoDTO crearDTO() {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Helado de Fresa");
        dto.setDescripcion("Helado artesanal de fresa");
        dto.setCategoria("Helados");
        dto.setPrecio(new BigDecimal("8500.00"));
        dto.setStock(30);
        dto.setEstado(true);
        return dto;
    }

    @Test
    void guardarProductoShouldPersistWhenNoDuplicateExists() {
        ProductoRepository repository = mock(ProductoRepository.class);
        ProductoService service = new ProductoService(repository);
        ProductoDTO dto = crearDTO();

        when(repository.existePorNombreYCategoriaExcluyendoId(dto.getNombre(), dto.getCategoria(), 0))
                .thenReturn(false);
        when(repository.save(any(Producto.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        Producto guardado = service.guardarProducto(dto);

        assertEquals("Helado de Fresa", guardado.getNombre());
        assertNotNull(guardado.getFechaRegistro());
        verify(repository, times(1)).save(any(Producto.class));
    }

    @Test
    void guardarProductoShouldThrowWhenDuplicateExists() {
        ProductoRepository repository = mock(ProductoRepository.class);
        ProductoService service = new ProductoService(repository);
        ProductoDTO dto = crearDTO();

        when(repository.existePorNombreYCategoriaExcluyendoId(dto.getNombre(), dto.getCategoria(), 0))
                .thenReturn(true);

        assertThrows(ProductoDuplicadoException.class, () -> service.guardarProducto(dto));
        verify(repository, never()).save(any(Producto.class));
    }

    @Test
    void buscarProductoShouldThrowWhenNotFound() {
        ProductoRepository repository = mock(ProductoRepository.class);
        ProductoService service = new ProductoService(repository);

        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ProductoNoEncontradoException.class, () -> service.buscarProducto(99));
    }

    @Test
    void eliminarProductoShouldDeleteWhenExists() {
        ProductoRepository repository = mock(ProductoRepository.class);
        ProductoService service = new ProductoService(repository);
        Producto producto = new Producto(1, "Helado de Mango", "Helados", new BigDecimal("9000.00"), 12, true);

        when(repository.findById(1)).thenReturn(Optional.of(producto));

        service.eliminarProducto(1);

        verify(repository, times(1)).delete(producto);
    }

    @Test
    void listarProductosShouldReturnRepositoryResultsSortedDescending() {
        ProductoRepository repository = mock(ProductoRepository.class);
        ProductoService service = new ProductoService(repository);
        Producto p1 = new Producto(1, "A", "Helados", new BigDecimal("1.00"), 1, true);
        Producto p2 = new Producto(2, "B", "Helados", new BigDecimal("2.00"), 2, true);

        when(repository.findAllByOrderByIdProductoDesc()).thenReturn(List.of(p2, p1));

        List<Producto> resultado = service.listarProductos();

        assertEquals(2, resultado.size());
        assertEquals(2, resultado.get(0).getIdProducto());
        assertEquals(1, resultado.get(1).getIdProducto());
    }
}
