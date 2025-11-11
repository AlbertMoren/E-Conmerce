<%@ page contentType="text/html;charset=UTF-8"%>

<%
    if(session.getAttribute("usuarioLogado") == null){
        response.sendRedirect("login.jsp");
        return;
    }
    String emailDoUsuario = (String) session.getAttribute("usuarioLogado");
%>

<html>
<head>
    <title>Bem-vindo, <%= emailDoUsuario %>!</title>

</head>
<body>
    Você está na sua área logada.

    <div style="text-align: center; margin-top: 20px; margin-bottom: 20px;">
        <a href="/logout.jsp">Sair da conta</a>
    </div>

    <h2>Atualizar Meus Dados</h2>

    <form action="atualizar" method="POST">

        <div class="form-group">
            <label>Novo Nome:</label>

            <input type="text" name="nome_usuario" required>
        </div>

        <div class="form-group">
            <label>Novo Email:</label>

            <input type="text" name="email_usuario" required>
        </div>

        <div class="form-group">
            <label>Nova Senha:</label>

            <input type="password" name="senha_usuario">
        </div>

        <button type="submit">Atualizar Dados</button>
    </form>

    <div style="text-align: center; margin-top: 20px; margin-bottom: 20px;">
        <a href="/remover">Excluir conta</a>
    </div>


</body>
</html>
