<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="includes/header.jsp" %>

<section class="welcome-band">
    <div class="row align-items-center g-4">
        <div class="col-lg-7">
            <p class="section-kicker mb-2">Sistema web HELAMIEL</p>
            <h1 class="display-title">Gestión de productos para heladería</h1>
            <p class="lead text-secondary mb-4">
                Módulo web desarrollado con Servlets, JSP, JDBC y MySQL para registrar,
                consultar, editar y eliminar productos del inventario.
            </p>
            <div class="d-flex flex-wrap gap-2">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/productos">
                    <i class="bi bi-table me-1"></i>Ver productos
                </a>
                <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/productos?accion=nuevo">
                    <i class="bi bi-plus-circle me-1"></i>Registrar producto
                </a>
            </div>
        </div>
        <div class="col-lg-5">
            <div class="status-panel">
                <div class="status-item">
                    <span class="status-icon bg-pink"><i class="bi bi-window"></i></span>
                    <div>
                        <strong>Aplicación web MVC</strong>
                        <span>Vista JSP, servlet controlador, DAO y modelo.</span>
                    </div>
                </div>
                <div class="status-item">
                    <span class="status-icon bg-mint"><i class="bi bi-database-check"></i></span>
                    <div>
                        <strong>Persistencia JDBC</strong>
                        <span>Conexión directa con la base de datos HELAMIEL.</span>
                    </div>
                </div>
                <div class="status-item">
                    <span class="status-icon bg-cream"><i class="bi bi-shield-check"></i></span>
                    <div>
                        <strong>Validaciones</strong>
                        <span>Reglas en navegador y servidor para datos limpios.</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="mt-4">
    <div class="row g-3">
        <div class="col-md-4">
            <div class="feature-tile">
                <i class="bi bi-plus-square"></i>
                <h2>Registrar</h2>
                <p>Alta de productos con nombre, categoría, precio, stock y estado.</p>
            </div>
        </div>
        <div class="col-md-4">
            <div class="feature-tile">
                <i class="bi bi-search"></i>
                <h2>Consultar</h2>
                <p>Listado responsive y búsqueda puntual por identificador.</p>
            </div>
        </div>
        <div class="col-md-4">
            <div class="feature-tile">
                <i class="bi bi-pencil-square"></i>
                <h2>Actualizar</h2>
                <p>Edición y eliminación con confirmación para evitar errores.</p>
            </div>
        </div>
    </div>
</section>

<%@ include file="includes/footer.jsp" %>
