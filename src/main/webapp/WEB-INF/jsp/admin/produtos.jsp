<%@include file="cabecalhoAdmin.jsp" %>
<%@include file="menuLateral.jsp" %>

<main class="dashboard-content">
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
                <form action="/removerAdmin" method="POST" onsubmit="return confirm('Remover este usuário?');">
                    <input type="hidden" name="email_para_remover" value="<%= user.getEmail() %>">
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

<%@include file="../public/rodape.jsp" %>