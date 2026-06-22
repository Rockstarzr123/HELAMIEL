<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="includes/header.jsp" %>

<div class="page-heading">
    <div>
        <p class="section-kicker mb-1">Registro</p>
        <h1>Nuevo producto</h1>
    </div>
    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/productos">
        <i class="bi bi-arrow-left me-1"></i>Volver
    </a>
</div>

<%@ include file="includes/mensajes.jsp" %>

<form class="form-surface" method="post" action="${pageContext.request.contextPath}/productos">
    <input type="hidden" name="accion" value="registrar">

    <div class="row g-3">
        <div class="col-md-6">
            <label class="form-label" for="nombre">Nombre</label>
            <input class="form-control" id="nombre" name="nombre" type="text"
                   value="${producto.nombre}" required minlength="3" maxlength="100"
                   pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ0-9 .,'-]+">
            <div class="form-text">Entre 3 y 100 caracteres. No use símbolos especiales.</div>
        </div>

        <div class="col-md-6">
            <label class="form-label" for="categoria">Categoría</label>
            <input class="form-control" id="categoria" name="categoria" type="text"
                   value="${producto.categoria}" required minlength="3" maxlength="50"
                   pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ0-9 .,'-]+">
            <div class="form-text">Ejemplo: Helados, Paletas, Bebidas o Conos.</div>
        </div>

        <div class="col-md-4">
            <label class="form-label" for="precio">Precio</label>
            <input class="form-control" id="precio" name="precio" type="number"
                   value="${producto.precio}" required min="1" step="0.01">
        </div>

        <div class="col-md-4">
            <label class="form-label" for="stock">Stock</label>
            <input class="form-control" id="stock" name="stock" type="number"
                   value="${producto.stock}" required min="1" step="1">
        </div>

        <div class="col-md-4">
            <label class="form-label" for="estado">Estado</label>
            <select class="form-select" id="estado" name="estado" required>
                <option value="true" ${producto.estado ? 'selected' : ''}>Activo</option>
                <option value="false" ${!producto.estado ? 'selected' : ''}>Inactivo</option>
            </select>
        </div>
    </div>

    <div class="form-actions">
        <button class="btn btn-primary" type="submit">
            <i class="bi bi-save me-1"></i>Registrar producto
        </button>
        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/productos">
            Cancelar
        </a>
    </div>
</form>

<%@ include file="includes/footer.jsp" %>
