package com.helamiel.helamiel.service;

import com.helamiel.helamiel.dto.ProductoDTO;
import com.helamiel.helamiel.exception.ProductoDuplicadoException;
import com.helamiel.helamiel.exception.ProductoNoEncontradoException;
import com.helamiel.helamiel.model.Producto;
import com.helamiel.helamiel.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Servicio para gestión de productos.
 */
@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public ProductoService() {
    }

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Obtiene todos los productos activos.
     */
    public List<Producto> getAllProductos() {
        return productoRepository.findByEstadoOrderByIdDesc(true);
    }

    /**
     * Obtiene un producto por su ID.
     */
    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    /**
     * Crea un nuevo producto.
     */
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Actualiza un producto existente.
     */
    public Producto actualizarProducto(Long id, Producto detallesProducto) {
        Optional<Producto> optional = productoRepository.findById(id);
        if (optional.isPresent()) {
            Producto producto = optional.get();
            producto.setNombre(detallesProducto.getNombre());
            producto.setDescripcion(detallesProducto.getDescripcion());
            producto.setCategoria(detallesProducto.getCategoria());
            producto.setPrecio(detallesProducto.getPrecio());
            producto.setStock(detallesProducto.getStock());
            producto.setEstado(detallesProducto.isEstado());
            producto.setFechaRegistro(detallesProducto.getFechaRegistro());
            return productoRepository.save(producto);
        }
        throw new RuntimeException("Producto no encontrado");
    }

    /**
     * Elimina un producto por ID (soft delete).
     */
    public void eliminarProducto(int id) {
        Producto producto = buscarProducto(id);
        productoRepository.delete(producto);
    }

    public void eliminarProducto(Long id) {
        Producto producto = buscarProducto(id);
        productoRepository.delete(producto);
    }

    /**
     * Busca productos por texto libre en nombre, descripción o categoría.
     */
    public List<Producto> buscarPorTexto(String texto) {
        return productoRepository.buscarPorTexto(texto);
    }

    /**
     * Busca productos por nombre y categoría.
     */
    public List<Producto> buscarProductos(String nombre, String categoria) {
        return productoRepository.buscarProductos(nombre, categoria);
    }

    /**
     * Obtiene productos por categoría.
     */
    public List<Producto> getProductosPorCategoria(String categoria) {
        return productoRepository.findByCategoriaIgnoreCaseOrderByIdDesc(categoria);
    }

    /**
     * Obtiene el listado completo de productos.
     */
    public List<Producto> listarProductos() {
        return productoRepository.findAllByOrderByIdProductoDesc();
    }

    /**
     * Obtiene las categorias unicas disponibles.
     */
    public List<String> listarCategorias() {
        return productoRepository.listarCategorias();
    }

    /**
     * Busca productos a partir de texto y estado opcionales.
     */
    public List<Producto> filtrarProductos(String categoria, Boolean estado) {
        if (categoria != null && !categoria.isBlank() && estado != null) {
            return productoRepository.findByCategoriaIgnoreCaseAndEstadoOrderByIdDesc(categoria, estado);
        }
        if (categoria != null && !categoria.isBlank()) {
            return productoRepository.findByCategoriaIgnoreCaseOrderByIdDesc(categoria);
        }
        if (estado != null) {
            return productoRepository.findByEstadoOrderByIdDesc(estado);
        }
        return listarProductos();
    }

    /**
     * Busca un producto por su ID y devuelve la entidad.
     */
    public Producto buscarProducto(int id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    public Producto buscarProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id.intValue()));
    }

    /**
     * Guarda un producto a partir de un DTO.
     */
    public Producto guardarProducto(ProductoDTO dto) {
        Objects.requireNonNull(dto, "El DTO no puede ser nulo.");
        if (productoRepository.existePorNombreYCategoriaExcluyendoId(dto.getNombre(), dto.getCategoria(), 0)) {
            throw new ProductoDuplicadoException(dto.getNombre(), dto.getCategoria());
        }
        return productoRepository.save(toProducto(dto));
    }

    /**
     * Edita un producto a partir de un DTO.
     */
    public Producto editarProducto(Long id, ProductoDTO dto) {
        Optional<Producto> optional = productoRepository.findById(id);
        if (optional.isPresent()) {
            Producto producto = optional.get();
            producto.setNombre(dto.getNombre());
            producto.setDescripcion(dto.getDescripcion());
            producto.setCategoria(dto.getCategoria());
            producto.setPrecio(dto.getPrecio());
            producto.setStock(dto.getStock());
            producto.setEstado(dto.isEstado());
            producto.setFechaRegistro(dto.getFechaRegistro() != null ? dto.getFechaRegistro() : LocalDateTime.now());
            return productoRepository.save(producto);
        }
        throw new RuntimeException("Producto no encontrado");
    }

    /**
     * Convierte un Producto a DTO.
     */
    public ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setCategoria(producto.getCategoria());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setEstado(producto.isEstado());
        dto.setFechaRegistro(producto.getFechaRegistro());
        return dto;
    }

    /**
     * Convierte DTO a Producto.
     */
    public Producto toProducto(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCategoria(dto.getCategoria());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setEstado(dto.isEstado());
        producto.setFechaRegistro(dto.getFechaRegistro() != null ? dto.getFechaRegistro() : LocalDateTime.now());
        return producto;
    }
}
