<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:if test="${not empty mensaje}">
    <div class="alert alert-${empty tipoMensaje ? 'info' : tipoMensaje} alert-dismissible fade show" role="alert">
        <i class="bi bi-info-circle me-2"></i>
        <c:out value="${mensaje}"/>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
    </div>
</c:if>

<c:if test="${not empty errores}">
    <div class="alert alert-danger" role="alert">
        <div class="fw-semibold mb-2">
            <i class="bi bi-exclamation-triangle me-2"></i>Revise los datos del formulario
        </div>
        <ul class="mb-0">
            <c:forEach var="error" items="${errores}">
                <li><c:out value="${error}"/></li>
            </c:forEach>
        </ul>
    </div>
</c:if>
