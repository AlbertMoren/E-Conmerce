<%@ page contentType="text/html;charset=UTF-8"%>
<%
    String msgErro = (String) request.getAttribute("mensagemErro");
    String msgSucesso = (String) request.getAttribute("mensagemSucesso");
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página de Login</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/estilo.css">

    <style>
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
            font-weight: bold;
            text-align: center;
        }
        .alert-success {
            color: #155724;
            background-color: #d4edda;
            border-color: #c3e6cb;
        }
        .alert-danger {
            color: #721c24;
            background-color: #f8d7da;
            border-color: #f5c6cb;
        }
    </style>

</head>
<body class="body-center-form">

<header class="top-banner">
    <div class="banner-content">
        <a href="<%= request.getContextPath() %>/home">
        <img src="${pageContext.request.contextPath}/assets/LOGO-ICON.png" alt="Logo Papoco" class="logo">
        </a>
    </div>
</header>

<div class="container">

    <!-- BLOCO DE MENSAGENS -->
    <% if (msgErro != null) { %>
    <div class="alert alert-danger"><%= msgErro %></div>
    <% } %>

    <% if (msgSucesso != null) { %>
    <div class="alert alert-success"><%= msgSucesso %></div>
    <% } %>


    <h2>Fazer Login</h2>

    <form action="<%= request.getContextPath()%>login" method="POST">

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email_usuario" required>
        </div>

        <div class="form-group">
            <label for="senha">Senha:</label>
            <input type="password" id="senha" name="senha_usuario" required>
        </div>

        <button type="submit">Entrar</button>

        <div style="text-align: center; margin-top: 20px; margin-bottom: 20px;">
            Não tem uma conta?
            <a href="<%= request.getContextPath() %>/cadastrar">Crie uma conta</a>
        </div>

        <div style="text-align: center; margin-top: 20px; margin-bottom: 20px;">
            Entrar sem login?
            <a href="<%= request.getContextPath() %>/home">Ver catálogo</a>
        </div>
    </form>

</div> </body>
</html>