<%@include file="cabecalhoAdmin.jsp" %>
<%@include file="menuLateral.jsp" %>
<%
    java.util.List<models.usuario.Usuario> listaUsuarios = (java.util.List<models.usuario.Usuario>) request.getAttribute("listaDeUsuarios");
%>
<main class="dashboard-content">
    <%
        String erro = (String) request.getAttribute("mensagemErro");
        String sucesso = (String) session.getAttribute("mensagemSucesso");
        if (sucesso != null) session.removeAttribute("mensagemSucesso"); // Limpa após exibir
    %>

    <% if (erro != null) { %>
    <div class="alert alert-danger"><%= erro %></div>
    <% } %>

    <% if (sucesso != null) { %>
    <div class="alert alert-success"><%= sucesso %></div>
    <% } %>

    <h2>Gerenciar Usuários</h2>
    <table class="user-table">
        <thead>
        <tr>
            <th>Nome</th>
            <th>Email</th>
            <th>Tipo</th>
            <th>Ação</th>
        </tr>
        </thead>

        <tbody>
        <%
            if (listaUsuarios != null) {
                for (models.usuario.Usuario user : listaUsuarios) {
        %>
        <tr>
            <td><%= user.getNome() %></td>
            <td><%= user.getEmail() %></td>
            <td><%= user.getAdministrador() ? "Admin" : "Cliente" %></td>
            <td>
                <form action="${pageContext.request.contextPath}/admin/usuarios" method="POST" onsubmit="return confirm('Remover este usuário?');">
                    <input type="hidden" name="email_para_remover" value="<%= user.getEmail() %>">
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
