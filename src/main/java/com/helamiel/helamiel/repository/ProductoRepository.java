package com.helamiel.helamiel.repository;

import com.helamiel.helamiel.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio Spring Data JPA para el acceso a datos de {@link Producto}.
 *
 * <p>Provee las operaciones CRUD basicas heredadas de {@link JpaRepository}
 * mas las consultas adicionales requeridas por el modulo de Gestion de
 * Productos (busqueda por texto, filtro por categoria y validacion de
 * duplicados).</p>
 */
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    /**
     * Lista todos los productos del mas reciente al mas antiguo.
     *
     * @return lista completa de productos ordenada por id descendente.
     */
    List<Producto> findAllByOrderByIdProductoDesc();

    /**
     * Busca productos cuyo nombre, descripcion o categoria contengan el
     * texto indicado, sin distinguir mayusculas/minusculas.
     *
     * @param texto texto a buscar.
     * @return lista de productos que coinciden con la busqueda.
     */
    @Query("""
            SELECT p FROM Producto p
            WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))
               OR LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))
               OR LOWER(p.categoria) LIKE LOWER(CONCAT('%', :texto, '%'))
            ORDER BY p.idProducto DESC
            """)
    List<Producto> buscarPorTexto(@Param("texto") String texto);

    /**
     * Lista los productos que pertenecen a una categoria especifica.
     *
     * @param categoria categoria por la cual filtrar.
     * @return lista de productos de la categoria indicada.
     */
    List<Producto> findByCategoriaIgnoreCaseOrderByIdProductoDesc(String categoria);

    /**
     * Lista los productos filtrando por su estado (activo/inactivo).
     *
     * @param estado estado por el cual filtrar.
     * @return lista de productos con el estado indicado.
     */
    List<Producto> findByEstadoOrderByIdProductoDesc(boolean estado);

    /**
     * Verifica si ya existe un producto con el mismo nombre y categoria,
     * excluyendo un identificador especifico (util al editar).
     *
     * @param nombre nombre del producto.
     * @param categoria categoria del producto.
     * @param idProducto identificador que se debe excluir de la busqueda.
     * @return true si existe otro producto con el mismo nombre y categoria.
     */
    @Query("""
            SELECT COUNT(p) > 0 FROM Producto p
            WHERE LOWER(p.nombre) = LOWER(:nombre)
              AND LOWER(p.categoria) = LOWER(:categoria)
              AND p.idProducto <> :idProducto
            """)
    boolean existePorNombreYCategoriaExcluyendoId(
            @Param("nombre") String nombre,
            @Param("categoria") String categoria,
            @Param("idProducto") int idProducto);

    /**
     * Obtiene todas las categorias distintas registradas, ordenadas
     * alfabeticamente, para alimentar el filtro del listado.
     *
     * @return lista de categorias unicas.
     */
    @Query("SELECT DISTINCT p.categoria FROM Producto p ORDER BY p.categoria ASC")
    List<String> listarCategorias();
}
