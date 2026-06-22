<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="includes/header.jsp" %>

<section class="message-page">
    <div class="message-icon">
        <i class="bi bi-info-circle"></i>
    </div>
    <h1>Mensaje del sistema</h1>
    <div class="alert alert-${empty tipoMensaje ? 'info' : tipoMensaje}" role="alert">
        <c:out value="${mensaje}"/>
    </div>
    <div class="d-flex justify-content-center gap-2">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/productos">
            <i class="bi bi-table me-1"></i>Ir a productos
        </a>
        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/">
            Inicio
        </a>
    </div>
</section>

<%@ include file="includes/footer.jsp" %>
