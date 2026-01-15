<%@ page contentType="text/html;charset=UTF-8"%>
<%
    java.util.List<models.usuario.Usuario> listaUsuarios = (java.util.List<models.usuario.Usuario>) request.getAttribute("listaDeUsuarios");
%>
<html>
<head>
    <title>Painel Admin - Gerenciar Usuários</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/estilo.css">
</head>

<body class="body-dashboard">
    <header class="top-banner">
        <div class="banner-content">
            <a href="${pageContext.request.contextPath}/home">
                <img src="${pageContext.request.contextPath}/assets/LOGO-ICON.png" alt="Logo Papoco" class="logo">
            </a>
        </div>
    </header>
    <div class="breadcrumb">
        <div class="breadcrumb-container">
            <a href="${pageContext.request.contextPath}/home">HOME</a> / PAINEL ADMIN
        </div>
    </div>
    <div class="dashboard-wrapper">

        <nav class="dashboard-sidebar">
            <h3>Painel Admin</h3>
            <ul class="sidebar-menu">
                <li><a href="${pageContext.request.contextPath}/admin/usuarios">Gerenciar Usuários</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/categorias">Gerenciar Categorias</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/produtos">Gerenciar Produtos</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Sair</a></li>
            </ul>
        </nav>

        <main class="dashboard-content">
            <h2>Gerenciar Usuários</h2>
            <p>Visão de todos os usuáriosa     --- cadastrados no sistema.</p>

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
    </div>
</body>
</html>