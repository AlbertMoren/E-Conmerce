<%@ page import="models.categoria.Categoria" %>
<%@include file="cabecalhoAdmin.jsp" %>
<%@include file="menuLateral.jsp" %>
<%
    java.util.List<models.categoria.Categoria> listaCategorias = (java.util.List<Categoria>) request.getAttribute("listaDeCategorias");
%>
<main class="dashboard-content">
    <%
        String msg = (String) session.getAttribute("mensagem");
        if (msg != null) {
    %>
    <div class="alert alert-info"><%= msg %></div>
    <%
            session.removeAttribute("mensagem");
        }
    %>
    <h2>Gerenciar Categorias</h2>
    <table class="user-table">
        <thead>
        <tr>
            <th>Nome</th>
            <th>Ação</th>
        </tr>
        </thead>

        <tbody>
        <%
            if (listaCategorias != null) {
                for (models.categoria.Categoria categoria : listaCategorias) {
        %>
        <tr>
            <td><%= categoria.getNome() %></td>
            <td>
                <form action="${pageContext.request.contextPath}/admin/categorias" method="POST" onsubmit="return confirm('Remover esta categoria?');">
                    <input type="hidden" name="id" value="<%= categoria.getId_cat() %>">
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
