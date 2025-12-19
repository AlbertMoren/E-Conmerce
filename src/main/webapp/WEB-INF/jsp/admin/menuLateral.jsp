<%@ page pageEncoding="UTF-8" %>
<div class="col-md-3 col-lg-2 d-md-block bg-dark sidebar min-vh-100 p-0 shadow" style="position: sticky; top: 0; left: 0; z-index: 100;">
    <div class="p-4 text-white border-bottom border-secondary mb-3">
        <h5 class="fw-bold mb-0 text-uppercase text-center" style="letter-spacing: 1px; font-size: 1.1rem;">Painel Admin</h5>
    </div>

    <div class="list-group list-group-flush">
        <a href="${pageContext.request.contextPath}/admin/usuarios" class="list-group-item list-group-item-action bg-dark text-white border-0 py-3 px-4">
            <i class="bi bi-people me-3"></i> Usuários
        </a>
        <a href="${pageContext.request.contextPath}/admin/categorias" class="list-group-item list-group-item-action bg-dark text-white border-0 py-3 px-4">
            <i class="bi bi-tags me-3"></i> Categorias
        </a>
        <a href="${pageContext.request.contextPath}/admin/produtos" class="list-group-item list-group-item-action bg-dark text-white border-0 py-3 px-4">
            <i class="bi bi-box-seam me-3"></i> Produtos
        </a>
        <a href="${pageContext.request.contextPath}/admin/estoque" target="_blank" class="list-group-item list-group-item-action bg-dark text-white border-0 py-3 px-4">
            <i class="bi bi-file-pdf me-3"></i> Relatório PDF
        </a>

        <div class="mt-5 px-4">
            <hr class="text-secondary">
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger w-100 btn-sm mt-2">
                <i class="bi bi-door-open me-2"></i> Sair
            </a>
        </div>
    </div>
</div>