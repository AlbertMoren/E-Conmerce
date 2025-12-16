<%@ page contentType="text/html;charset=UTF-8"%>
<%

    String msgSucesso = (String) request.getAttribute("mensagemSucesso");
    String msgErro = (String) request.getAttribute("mensagemErro");


    if (msgSucesso != null) {

        response.setHeader("Refresh", "3;url=login.jsp");
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Criar Conta</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body class="body-center-form"> <header class="top-banner">
    <div class="banner-content">
        <a href="index.jsp"> <img src="assets/LOGO-ICON.png" alt="Logo Papoco" class="logo"></a>
    </div>
</header>

<div class="container">

    <%
        if (msgSucesso != null) {
            out.println("<div class='alert alert-success'>" + msgSucesso + "</div>");
        }
        if (msgErro != null) {
            out.println("<div class='alert alert-danger'>" + msgErro + "</div>");
        }
    %>

    <h2>Criar Conta</h2>

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
        <button type="submit">Cadastrar</button>

        <div style="text-align: center; margin-top: 20px;">
            Já tem uma conta?
            <a href="login.jsp">Fazer login</a>
        </div>
    </form>
</div>
</body>
</html>