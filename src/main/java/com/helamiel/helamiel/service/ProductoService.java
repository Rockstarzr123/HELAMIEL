package com.helamiel.helamiel.service;

import com.helamiel.helamiel.dto.ProductoDTO;
import com.helamiel.helamiel.exception.ProductoDuplicadoException;
import com.helamiel.helamiel.exception.ProductoNoEncontradoException;
import com.helamiel.helamiel.model.Producto;
import com.helamiel.helamiel.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Contiene la logica de negocio del modulo de Gestion de Productos:
 * registro, consulta, edicion, eliminacion, busqueda y filtrado.
 *
 * <p>Se apoya en {@link ProductoRepository} (Spring Data JPA) para el
 * acceso a datos y aplica las validaciones de negocio (duplicados,
 * existencia) antes de delegar en el repositorio.</p>
 */
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    /**
     * Crea el servicio inyectando el repositorio de productos.
     *
     * @param productoRepository repositorio JPA de productos.
     */
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Lista todos los productos registrados, del mas reciente al mas antiguo.
     *
     * @return lista completa de productos.
     */
    public List<Producto> listarProductos() {
        return productoRepository.findAllByOrderByIdProductoDesc();
    }

    /**
     * Busca un producto por su identificador.
     *
     * @param idProducto identificador del producto.
     * @return producto encontrado.
     * @throws ProductoNoEncontradoException si no existe un producto con ese id.
     */
    public Producto buscarProducto(int idProducto) {
        return productoRepository.findById(idProducto)
                .orElseThrow(() -> new ProductoNoEncontradoException(idProducto));
    }

    /**
     * Registra un nuevo producto a partir de los datos del formulario.
     *
     * @param productoDTO datos del producto a registrar.
     * @return producto guardado, con su identificador generado.
     * @throws ProductoDuplicadoException si ya existe un producto con el
     *         mismo nombre y categoria.
     */
    public Producto guardarProducto(ProductoDTO productoDTO) {
        validarDuplicado(productoDTO, 0);

        Producto producto = new Producto();
        copiarDatos(productoDTO, producto);
        producto.setFechaRegistro(LocalDateTime.now());

        return productoRepository.save(producto);
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param idProducto identificador del producto a editar.
     * @param productoDTO datos actualizados del producto.
     * @return producto actualizado.
     * @throws ProductoNoEncontradoException si el producto no existe.
     * @throws ProductoDuplicadoException si los nuevos datos chocan con otro
     *         producto existente.
     */
    @SuppressWarnings("null")
    public Producto editarProducto(int idProducto, ProductoDTO productoDTO) {
        Producto producto = buscarProducto(idProducto);

        validarDuplicado(productoDTO, idProducto);

        copiarDatos(productoDTO, producto);

        return productoRepository.save(producto);
    }

    /**
     * Elimina un producto por su identificador.
     *
     * @param idProducto identificador del producto a eliminar.
     * @throws ProductoNoEncontradoException si el producto no existe.
     */
    @SuppressWarnings("null")
    public void eliminarProducto(int idProducto) {
        Producto producto = buscarProducto(idProducto);
        productoRepository.delete(producto);
    }

    /**
     * Busca productos por texto libre (nombre, descripcion o categoria).
     *
     * @param texto texto a buscar; si es nulo o vacio se listan todos.
     * @return lista de productos que coinciden con la busqueda.
     */
    public List<Producto> buscarPorTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            return listarProductos();
        }
        return productoRepository.buscarPorTexto(texto.trim());
    }

    /**
     * Filtra productos por categoria y/o estado.
     *
     * @param categoria categoria por la cual filtrar (opcional).
     * @param estado estado por el cual filtrar (opcional).
     * @return lista de productos que cumplen el filtro solicitado.
     */
    public List<Producto> filtrarProductos(String categoria, Boolean estado) {
        List<Producto> productos = listarProductos();

        if (categoria != null && !categoria.isBlank()) {
            productos = productos.stream()
                    .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                    .toList();
        }

        if (estado != null) {
            productos = productos.stream()
                    .filter(p -> p.isEstado() == estado)
                    .toList();
        }

        return productos;
    }

    /**
     * Obtiene la lista de categorias distintas registradas, para alimentar
     * el filtro del listado en la interfaz.
     *
     * @return lista de categorias unicas ordenadas alfabeticamente.
     */
    public List<String> listarCategorias() {
        return productoRepository.listarCategorias();
    }

    /**
     * Valida que no exista otro producto con el mismo nombre y categoria.
     *
     * @param productoDTO datos a validar.
     * @param idProductoExcluir identificador que se debe excluir de la
     *        validacion (0 cuando es un registro nuevo).
     * @throws ProductoDuplicadoException si se encuentra un duplicado.
     */
    private void validarDuplicado(ProductoDTO productoDTO, int idProductoExcluir) {
        boolean existe = productoRepository.existePorNombreYCategoriaExcluyendoId(
                productoDTO.getNombre(), productoDTO.getCategoria(), idProductoExcluir);

        if (existe) {
            throw new ProductoDuplicadoException(productoDTO.getNombre(), productoDTO.getCategoria());
        }
    }

    /**
     * Copia los datos del DTO del formulario hacia la entidad JPA.
     *
     * @param origen datos capturados en el formulario.
     * @param destino entidad que recibira los datos.
     */
    private void copiarDatos(ProductoDTO origen, Producto destino) {
        destino.setNombre(origen.getNombre().trim());
        destino.setDescripcion(origen.getDescripcion() == null ? null : origen.getDescripcion().trim());
        destino.setCategoria(origen.getCategoria().trim());
        destino.setPrecio(origen.getPrecio());
        destino.setStock(origen.getStock());
        destino.setEstado(origen.isEstado());
    }
}
