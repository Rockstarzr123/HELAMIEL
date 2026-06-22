<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="includes/header.jsp" %>

<div class="page-heading">
    <div>
        <p class="section-kicker mb-1">Confirmación</p>
        <h1>Eliminar producto</h1>
    </div>
    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/productos">
        <i class="bi bi-arrow-left me-1"></i>Volver
    </a>
</div>

<div class="confirm-panel">
    <div class="confirm-icon">
        <i class="bi bi-exclamation-triangle"></i>
    </div>
    <div>
        <h2>¿Desea eliminar este producto?</h2>
        <p class="text-secondary mb-3">
            Esta acción eliminará el registro seleccionado de la base de datos.
        </p>
        <dl class="row mb-4">
            <dt class="col-sm-3">ID</dt>
            <dd class="col-sm-9"><c:out value="${producto.idProducto}"/></dd>
            <dt class="col-sm-3">Nombre</dt>
            <dd class="col-sm-9"><c:out value="${producto.nombre}"/></dd>
            <dt class="col-sm-3">Categoría</dt>
            <dd class="col-sm-9"><c:out value="${producto.categoria}"/></dd>
        </dl>

        <form method="post" action="${pageContext.request.contextPath}/productos" class="d-flex gap-2">
            <input type="hidden" name="accion" value="eliminarConfirmado">
            <input type="hidden" name="idProducto" value="${producto.idProducto}">
            <button class="btn btn-danger" type="submit">
                <i class="bi bi-trash me-1"></i>Eliminar
            </button>
            <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/productos">
                Cancelar
            </a>
        </form>
    </div>
</div>

<%@ include file="includes/footer.jsp" %>
