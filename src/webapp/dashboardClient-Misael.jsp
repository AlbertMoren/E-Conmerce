<%@ page contentType="text/html;charset=UTF-8"%>
<%
    // Bloco de segurança (seu código original)
    if(session.getAttribute("usuarioLogado") == null){
        response.sendRedirect("login.jsp");
        return;
    }
    String emailDoUsuario = (String) session.getAttribute("usuarioLogado");

    String msgSucesso = (String) request.getAttribute("mensagemSucesso");
    String msgErro = (String) request.getAttribute("mensagemErro");
%>
<html>
<head>
    <title>Minha Conta - <%= emailDoUsuario %></title>
    <link rel="stylesheet" href="estilo2.css">
</head>

<body class="body-dashboard">

<header class="top-banner">
    <div class="banner-content">
        <a href="index.jsp"> <img src="assets/LOGO-ICON.png" alt="Logo Papoco" class="logo">
        </a>
    </div>
</header>

<div class="breadcrumb">
    <div class="breadcrumb-container">
        <a href="index.jsp">HOME</a> / MINHA CONTA
    </div>
</div>

<div class="dashboard-wrapper">

    <nav class="dashboard-sidebar">
        <h3>Minha Conta</h3>
        <ul class="sidebar-menu">
            <li class="active"><a href="dashboard.jsp">Meus Dados</a></li>
            <li><a href="#">Meus Pedidos</a></li> <li><a href="logout.jsp">Sair</a></li>
        </ul>
    </nav>

    <main class="dashboard-content">

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

        <form action="atualizar" method="POST">
            <h2>Editar Dados Cadastrais</h2>

            <div class="form-group">
                <label>Nome:</label>
                <input type="text" name="nome_usuario" required>
            </div>

            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email_usuario" value="<%= emailDoUsuario %>" required>
            </div>

            <div class="form-group">
                <label>Endereço:</label>
                <input type="text" name="endereco_usuario">
            </div>

            <div class="form-group">
                <label>Nova Senha (deixe em branco para não alterar):</label>
                <input type="password" name="senha_usuario">
            </div>

            <button type="submit">Salvar Alterações</button>
        </form>

        <div class="delete-section">
            <form action="remover" method="POST"
                  onsubmit="return confirm('Tem certeza que deseja excluir sua conta permanentemente?');">

                <h4>Remover Conta</h4>
                <p>Esta ação é permanente e não pode ser desfeita.</p>
                <button type="submit" class="btn-danger">Excluir Minha Conta</button>
            </form>
        </div>

    </main>
</div>

</body>
</html>