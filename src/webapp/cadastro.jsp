<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página de Cadastro</title>

    <link rel="stylesheet" href="estilo.css">

</head>
<body>

<header class="top-banner">
    <div class="banner-content">
        <img src="assets/LOGO-ICON.png" alt="Logo Papoco" class="logo">
    </div>
</header>

<%
    String msgSucesso = (String) request.getAttribute("mensagemSucesso");
    String msgErro = (String) request.getAttribute("mensagemErro");
%>




<div class="container">

    <h2>Cadastre-se</h2>

    <form action="cadastrar" method="POST">

        <div class="form-group">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome_usuario" required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email_usuario" required>
        </div>

        <div class="form-group">
            <label for="senha">Senha:</label>
            <input type="password" id="senha" name="senha_usuario" required>
        </div>

        <div style="text-align: center; margin-top: 20px; margin-bottom: 20px;">
                Já tem uma conta?
                <a href="/login.jsp">Faça o login</a>
        </div>



        <button type="submit">Cadastrar</button>
    </form>

    <% if (msgSucesso != null) { %>
    <div style="color: green; border: 1px solid green; padding: 10px; margin-bottom: 15px;">
        <%= msgSucesso %>
    </div>
    <% } %>

    <% if (msgErro != null) { %>
    <div style="color: red; border: 1px solid red; padding: 10px; margin-bottom: 15px;">
        <%= msgErro %>
    </div>
    <% } %>


    </div>
</body>

</html>