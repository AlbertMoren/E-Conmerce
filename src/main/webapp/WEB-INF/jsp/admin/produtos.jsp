<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="models.produto.Produto" %>
<%@include file="cabecalhoAdmin.jsp" %>
<%@include file="menuLateral.jsp" %>
<%
    java.util.List<models.produto.Produto> listaProdutos = (java.util.List<Produto>) request.getAttribute("listaDeProdutos");
%>
<main class="dashboard-content">
    <%
        String erro = (String) session.getAttribute("mensagemErro");
        if (erro != null) session.removeAttribute("mensagemErro");

        String sucesso = (String) session.getAttribute("mensagemSucesso");
        if (sucesso != null) session.removeAttribute("mensagemSucesso");
    %>

    <% if (erro != null) { %>
    <div class="alert alert-danger"><%= erro %></div>
    <% } %>

    <% if (sucesso != null) { %>
    <div class="alert alert-success"><%= sucesso %></div>
    <% } %>

    <h2>Gerenciar Produtos</h2>
    <table class="user-table">
        <thead>
        <tr>
            <th>Nome</th>
            <th>Preço</th>
            <th>Quantidade</th>
            <th>Categoria</th>
            <th>Ação</th>
        </tr>
        </thead>

        <tbody>
        <%
            if (listaProdutos != null) {
                for (models.produto.Produto produto : listaProdutos) {
        %>
        <tr>
            <td><%= produto.getDescricao() %></td>
            <td><%= produto.getPreco() %></td>
            <td><%= produto.getQuantidade() %></td>
            <td><%= produto.getCategoria().getNome() %></td>
            <td>
                <form action="${pageContext.request.contextPath}/admin/produtos" method="POST" onsubmit="return confirm('Remover este produto?');">
                    <input type="hidden" name="id" value="<%= produto.getId_produto() %>">
                    <input type="hidden" name="acao" value="remover">
                    <button type="submit" class="btn-danger">Remover</button>
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</main>

<%@include file="rodapeAdmin.jsp" %>
