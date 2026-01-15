<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="models.produto.Produto" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<%@include file="cabecalhoAdmin.jsp" %>
<%@include file="menuLateral.jsp" %>

<%
    java.util.List<models.produto.Produto> listaProdutos = (java.util.List<Produto>) request.getAttribute("listaDeProdutos");
    java.util.List<models.categoria.Categoria> cats = (java.util.List<models.categoria.Categoria>) request.getAttribute("listaDeCategorias");
%>

<main class="container-fluid py-4">
    <%
        String erro = (String) session.getAttribute("mensagemErro");
        if (erro != null) session.removeAttribute("mensagemErro");
        String sucesso = (String) session.getAttribute("mensagemSucesso");
        if (sucesso != null) session.removeAttribute("mensagemSucesso");
    %>

    <h2 class="h3 mb-0  text-gray-800">Gerenciar Produtos</h2>

    <div class="card shadow-sm mb-5">
        <div class="card-header bg-dark text-white font-weight-bold">Novo Produto</div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/produtos" method="POST" enctype="multipart/form-data" class="row g-3">
                <input type="hidden" name="acao" value="cadastrar">

                <div class="col-md-4">
                    <label class="form-label">Descrição</label>
                    <input type="text" name="descricao" class="form-control" required>
                </div>
                <div class="col-md-2">
                    <label class="form-label">Preço</label>
                    <input type="number" name="preco" step="0.01" class="form-control" required>
                </div>
                <div class="col-md-2">
                    <label class="form-label">Quantidade</label>
                    <input type="number" name="quantidade" class="form-control" required>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Categoria</label>
                    <select name="id_categoria" class="form-select" required>
                        <option value="">Selecione...</option>
                        <% if(cats != null) { for(models.categoria.Categoria c : cats) { %>
                        <option value="<%= c.getId_cat() %>"><%= c.getNome() %></option>
                        <% } } %>
                    </select>
                </div>
                <div class="col-md-9">
                    <label class="form-label">Foto do Produto</label>
                    <input type="file" name="foto" class="form-control" accept="image/*">
                </div>
                <div class="col-md-3 d-flex align-items-end">
                    <button type="submit" class="btn btn-success w-100">Adicionar Produto</button>
                </div>
            </form>
        </div>
    </div>

    <div class="card shadow-sm">
        <table class="table table-hover align-middle mb-0 text-center">
            <thead class="table-dark">
            <tr>
                <th>Foto</th>
                <th>Descrição</th>
                <th>Preço</th>
                <th>Quantidade</th>
                <th>Categoria</th>
                <th>Ações</th>
            </tr>
            </thead>

<%--            <tbody>--%>
<%--            <% if (listaProdutos != null) {--%>
<%--                for (models.produto.Produto p : listaProdutos) { %>--%>
<%--            <tr>--%>
<%--                <td>--%>
<%--                    <img src="${pageContext.request.contextPath}/public/mostrarFotoProduto?id=<%= p.getId_produto() %>"--%>
<%--                         alt="Foto" style="width: 50px; height: 50px; object-fit: cover;" class="rounded border">--%>
<%--                </td>--%>
<%--                <td>--%>
<%--                    <form action="${pageContext.request.contextPath}/admin/produtos" method="POST" class="m-0">--%>
<%--                        <input type="hidden" name="id" value="<%= p.getId_produto() %>">--%>
<%--                        <input type="hidden" name="acao" value="atualizar">--%>
<%--                        <input type="text" name="descricao" value="<%= p.getDescricao() %>" class="form-control form-control-sm">--%>
<%--                </td>--%>
<%--                <td><input type="number" name="preco" step="0.01" value="<%= p.getPreco() %>" class="form-control form-control-sm"></td>--%>
<%--                <td><input type="number" name="quantidade" value="<%= p.getQuantidade() %>" class="form-control form-control-sm"></td>--%>
<%--                <td>--%>
<%--                    <div class="d-flex gap-1 justify-content-center">--%>
<%--                        <button type="submit" class="btn btn-warning btn-sm">--%>
<%--                            <i class="bi bi-save me-1"></i> Salvar</button>--%>
<%--                        </form>--%>
<%--                        <form action="${pageContext.request.contextPath}/admin/produtos" method="POST" class="m-0">--%>
<%--                            <input type="hidden" name="id" value="<%= p.getId_produto() %>">--%>
<%--                            <input type="hidden" name="acao" value="remover">--%>
<%--                            <button type="submit" class="btn btn-outline-danger btn-sm px-3">--%>
<%--                                    <i class="bi bi-trash3 me-1"></i> Remover</button>--%>
<%--                        </form>--%>
<%--                    </div>--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--            <% } } %>--%>
<%--            </tbody>--%>
            <tbody>
            <% if (listaProdutos != null) {
                for (models.produto.Produto p : listaProdutos) { %>
            <tr>
                <td>
                    <img src="${pageContext.request.contextPath}/public/mostrarFotoProduto?id=<%= p.getId_produto() %>"
                         alt="Foto" style="width: 50px; height: 50px; object-fit: cover;" class="rounded border">
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/produtos" method="POST" enctype="multipart/form-data" class="m-0">
                        <input type="hidden" name="id" value="<%= p.getId_produto() %>">
                        <input type="hidden" name="acao" value="atualizar">
                        <input type="text" name="descricao" value="<%= p.getDescricao() %>" class="form-control form-control-sm">
                </td>
                <td><input type="number" name="preco" step="0.01" value="<%= p.getPreco() %>" class="form-control form-control-sm"></td>
                <td><input type="number" name="quantidade" value="<%= p.getQuantidade() %>" class="form-control form-control-sm"></td>
                <td>
                <select name="id_categoria" class="form-select" required>
                    <option value="<%= p.getCategoria().getId_cat() %>"><%= p.getCategoria().getNome() %></option>
                    <% if(cats != null) { for(models.categoria.Categoria c : cats) { %>
                        <% if (c.getId_cat() != p.getCategoria().getId_cat() ) { %>
                            <option value="<%= c.getId_cat() %>"><%= c.getNome() %></option>
                        <% } %>
                    <% } } %>
                </select>
                </td>
                <td>
                    <div class="d-flex gap-1 justify-content-center align-items-center">

                        <input type="file" name="foto" id="input-foto-<%= p.getId_produto() %>" class="d-none" accept="image/*">

                        <label for="input-foto-<%= p.getId_produto() %>" class="btn btn-outline-secondary btn-sm" title="Alterar Foto" style="cursor: pointer;">
                            <i class="bi bi-camera"></i>
                        </label>

                        <button type="submit" class="btn btn-warning btn-sm">
                            <i class="bi bi-save me-1"></i>
                        </button>
                        </form> <form action="${pageContext.request.contextPath}/admin/produtos" method="POST" class="m-0">
                        <input type="hidden" name="id" value="<%= p.getId_produto() %>">
                        <input type="hidden" name="acao" value="remover">
                        <button type="submit" class="btn btn-outline-danger btn-sm px-3">
                            <i class="bi bi-trash3"></i>
                        </button>
                    </form>
                    </div>
                </td>
            </tr>
            <% } } %>
            </tbody>

        </table>
    </div>
</main>
<%@include file="rodapeAdmin.jsp" %>