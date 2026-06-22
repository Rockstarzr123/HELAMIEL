<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="includes/header.jsp" %>

<div class="page-heading">
    <div>
        <p class="section-kicker mb-1">Inventario</p>
        <h1>Productos registrados</h1>
    </div>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/productos?accion=nuevo">
        <i class="bi bi-plus-circle me-1"></i>Nuevo producto
    </a>
</div>

<%@ include file="includes/mensajes.jsp" %>

<form class="toolbar-form mb-3" method="get" action="${pageContext.request.contextPath}/productos">
    <input type="hidden" name="accion" value="buscar">
    <label class="form-label mb-0" for="idBusqueda">Buscar por ID</label>
    <input class="form-control" id="idBusqueda" name="id" type="number" min="1"
           placeholder="Ej. 1" value="${idBusqueda}">
    <button class="btn btn-outline-primary" type="submit">
        <i class="bi bi-search me-1"></i>Buscar
    </button>
    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/productos">
        <i class="bi bi-arrow-clockwise me-1"></i>Ver todos
    </a>
</form>

<div class="table-responsive data-surface">
    <table class="table table-hover align-middle mb-0">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Categoría</th>
            <th>Precio</th>
            <th>Stock</th>
            <th>Estado</th>
            <th class="text-end">Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty productos}">
                <tr>
                    <td colspan="7" class="text-center text-secondary py-4">
                        <i class="bi bi-inbox me-1"></i>No hay productos para mostrar.
                    </td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="producto" items="${productos}">
                    <tr>
                        <td><c:out value="${producto.idProducto}"/></td>
                        <td class="fw-semibold"><c:out value="${producto.nombre}"/></td>
                        <td><c:out value="${producto.categoria}"/></td>
                        <td>$ <c:out value="${producto.precio}"/></td>
                        <td><c:out value="${producto.stock}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${producto.estado}">
                                    <span class="badge text-bg-success">Activo</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge text-bg-secondary">Inactivo</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="text-end actions-cell">
                            <a class="btn btn-sm btn-outline-primary"
                               href="${pageContext.request.contextPath}/productos?accion=editar&id=${producto.idProducto}"
                               title="Editar producto">
                                <i class="bi bi-pencil-square"></i>
                            </a>
                            <a class="btn btn-sm btn-outline-danger"
                               href="${pageContext.request.contextPath}/productos?accion=eliminar&id=${producto.idProducto}"
                               title="Eliminar producto">
                                <i class="bi bi-trash"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>

<%@ include file="includes/footer.jsp" %>
