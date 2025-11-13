<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página de Login</title>

    <link rel="stylesheet" href="estilo.css">

</head>
<body class="body-center-form">

<header class="top-banner">
    <div class="banner-content">
        <img src="assets/LOGO-ICON.png" alt="Logo Papoco" class="logo">
    </div>
</header>




<div class="container">

    <h2>Fazer Login</h2>

    <form action="logar" method="POST">


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
            <a href="/cadastro.jsp">Crie uma conta</a>
        </div>


    </form>

</div> </body>
</html>