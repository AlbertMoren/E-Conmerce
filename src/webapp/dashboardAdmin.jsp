<%@ page contentType="text/html;charset=UTF-8"%>
<%

    if(session.getAttribute("usuarioLogado") == null){
        response.sendRedirect("login.jsp");
        return;
    }


    Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    if(isAdmin == null || !isAdmin) {

        response.sendRedirect("dashboard.jsp");
        return;
    }


    java.util.List<models.Usuario> listaUsuarios = (java.util.List<models.Usuario>) request.getAttribute("listaDeUsuarios");
%>
<html>
<head>
    <title>Painel Admin - Gerenciar Usuários</title>
    <link rel="stylesheet" href="/estilo.css">
</head>

<body class="body-dashboard">

<header class="top-banner">
    <div class="banner-content">
        <a href="/index.jsp"> <img src="/assets/LOGO-ICON.png" alt="Logo Papoco" class="logo"></a>
    </div>
</header>

<div class="breadcrumb">
    <div class="breadcrumb-container">
        <a href="/index.jsp">HOME</a> / PAINEL ADMIN
    </div>
</div>

<div class="dashboard-wrapper">

    <nav class="dashboard-sidebar">
        <h3>Painel Admin</h3>
        <ul class="sidebar-menu">
            <li class="active"><a href="/admin/painel">Gerenciar Usuários</a></li>
            <li><a href="#">Gerenciar Produtos</a></li> <li><a href="/logout.jsp">Sair</a></li>
        </ul>
    </nav>

    <main class="dashboard-content">
        <h2>Gerenciar Usuários</h2>
        <p>Visão de todos os usuários cadastrados no sistema.</p>

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
                    for (models.Usuario user : listaUsuarios) {
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
</div>

</body>
</html>