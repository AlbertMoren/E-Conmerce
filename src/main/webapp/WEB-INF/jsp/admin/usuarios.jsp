<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="cabecalhoAdmin.jsp" %>
<%@include file="menuLateral.jsp" %>
<%
    java.util.List<models.usuario.Usuario> listaUsuarios = (java.util.List<models.usuario.Usuario>) request.getAttribute("listaDeUsuarios");
%>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

<main class="container-fluid py-4">
    <%
        String erro = (String) session.getAttribute("mensagemErro");
        if (erro != null) session.removeAttribute("mensagemErro");

        String sucesso = (String) session.getAttribute("mensagemSucesso");
        if (sucesso != null) session.removeAttribute("mensagemSucesso");
    %>

    <div class="row mb-3">
        <div class="col-12">
            <% if (erro != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-octagon-fill me-2"></i> <%= erro %>
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
        <h2 class="h3 mb-0 text-gray-800">Gerenciar Usuários</h2>
        <span class="badge bg-primary rounded-pill">
            <%= (listaUsuarios != null) ? listaUsuarios.size() : 0 %> cadastrados
        </span>
    </div>

    <div class="card shadow-sm border-0">
        <div class="card-header bg-dark text-white py-3">
            <h6 class="m-0 fw-bold"><i class="bi bi-people-fill me-2"></i>Lista de Usuários</h6>
        </div>
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light">
                    <tr>
                        <th class="ps-4">Nome</th>
                        <th>Email</th>
                        <th class="text-center">Tipo de Acesso</th>
                        <th class="text-center pe-4">Ação</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                            for (models.usuario.Usuario user : listaUsuarios) {
                    %>
                    <tr>
                        <td class="ps-4 fw-medium text-dark"><%= user.getNome() %></td>
                        <td><%= user.getEmail() %></td>
                        <td class="text-center">
                            <% if (user.getAdministrador()) { %>
                            <span class="badge bg-info text-dark">
                                        <i class="bi bi-shield-lock-fill me-1"></i> Admin
                                    </span>
                            <% } else { %>
                            <span class="badge bg-light text-secondary border">
                                        <i class="bi bi-person me-1"></i> Cliente
                                    </span>
                            <% } %>
                        </td>
                        <td class="text-center pe-4">
                            <form action="${pageContext.request.contextPath}/admin/usuarios" method="POST" onsubmit="return confirm('Tem certeza que deseja remover este usuário?');" class="m-0">
                                <input type="hidden" name="email_para_remover" value="<%= user.getEmail() %>">
                                <input type="hidden" name="acao" value="remover">
                                <button type="submit" class="btn btn-outline-danger btn-sm px-3">
                                    <i class="bi bi-trash3 me-1"></i> Remover
                                </button>
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="4" class="text-center py-5 text-muted">
                            <i class="bi bi-people display-4 d-block mb-2"></i>
                            Nenhum usuário encontrado.
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<%@include file="rodapeAdmin.jsp" %>