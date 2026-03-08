<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="models.categoria.Categoria" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<%@include file="cabecalhoAdmin.jsp" %>
<%@include file="menuLateral.jsp" %>

<%
    java.util.List<models.categoria.Categoria> listaCategorias = (java.util.List<Categoria>) request.getAttribute("listaDeCategorias");
%>

<main class="container-fluid py-4">
    <%
        // Recuperação de mensagens da sessão
        String erro = (String) session.getAttribute("mensagemErro");
        if (erro != null) session.removeAttribute("mensagemErro");

        String sucesso = (String) session.getAttribute("mensagemSucesso");
        if (sucesso != null) session.removeAttribute("mensagemSucesso");
    %>

    <div class="row">
        <div class="col-12">
            <% if (erro != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i> <%= erro %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <% } %>

            <% if (sucesso != null) { %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle-fill me-2"></i> <%= sucesso %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <% } %>
        </div>
    </div>

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="h3 mb-0 text-gray-800">Gerenciar Categorias</h2>
    </div>

    <div class="card shadow-sm mb-4">
        <div class="card-header bg-dark text-white py-3">
            <h6 class="m-0 font-weight-bold">Adicionar Nova Categoria</h6>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/categorias" method="POST" class="row g-3">
                <input type="hidden" name="acao" value="cadastrar">
                <div class="col-md-8 col-lg-6">
                    <input type="text" name="nome" class="form-control" placeholder="Nome da categoria" required>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-success px-4">
                        <i class="bi bi-plus-lg"></i> Adicionar
                    </button>
                </div>
            </form>
        </div>
    </div>

    <div class="card shadow-sm">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                <tr>
                    <th class="ps-4" style="width: 65%;">Categoria</th>
                    <th class="text-center" style="width: 35%;">Ações de Controle</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (listaCategorias != null && !listaCategorias.isEmpty()) {
                        for (models.categoria.Categoria cat : listaCategorias) {
                %>
                <tr>
                    <td class="ps-4">
                        <form action="${pageContext.request.contextPath}/admin/categorias" method="POST" class="d-flex gap-2 m-0">
                            <input type="hidden" name="id" value="<%= cat.getId_cat() %>">
                            <input type="hidden" name="acao" value="atualizar">

                            <input type="text" name="nome" value="<%= cat.getNome() %>" class="form-control form-control-sm w-75" required>

                            <button type="submit" class="btn btn-warning btn-sm text-dark px-3 fw-bold">
                                <i class="bi bi-save me-1"></i> Salvar
                            </button>
                        </form>
                    </td>

                    <td>
                        <div class="d-flex justify-content-center">
                            <form action="${pageContext.request.contextPath}/admin/categorias" method="POST" class="m-0" onsubmit="return confirm('Excluir esta categoria?');">
                                <input type="hidden" name="id" value="<%= cat.getId_cat() %>">
                                <input type="hidden" name="acao" value="remover">

                                <button type="submit" class="btn btn-outline-danger btn-sm px-3">
                                    <i class="bi bi-trash3 me-1"></i> Remover
                                </button>
                            </form>
                        </div>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="2" class="text-center py-5 text-muted small">
                        Nenhuma categoria encontrada no banco de dados.
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</main>

<%@include file="rodapeAdmin.jsp" %>