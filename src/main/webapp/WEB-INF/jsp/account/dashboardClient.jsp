<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="models.usuario.Usuario" %>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

    String msgSucesso = (String) request.getAttribute("mensagemSucesso");
    String msgErro = (String) request.getAttribute("mensagemErro");
%>

<jsp:include page="../public/cabecalho.jsp" />
<html>
<head>
    <title>Minha Conta - <%= usuario.getEmail() %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/estilo.css">
</head>

<body class="body-dashboard">

<div class="breadcrumb">
    <div class="breadcrumb-container">
        <a href="<%= request.getContextPath() %>/home">HOME</a> / MINHA CONTA
    </div>
</div>

<div class="dashboard-wrapper">

    <nav class="dashboard-sidebar">
        <h3>Minha Conta</h3>
        <ul class="sidebar-menu">
            <li class="active"><a href="#">Meus Dados</a></li>
            <li><a href="#">Meus Pedidos</a></li>
            <li><a href="<%= request.getContextPath() %>/logout">Sair</a></li>
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

        <form action="<%= request.getContextPath() %>/account/update" method="POST">
            <h2>Editar Dados Cadastrais</h2>

            <div class="form-group">
                <label>Nome:</label>
                <input type="text" name="nome_usuario" value="<%= usuario.getNome() %>">
            </div>

            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email_usuario" value="<%= usuario.getEmail() %>">
            </div>

            <div class="form-group">
                <label>Endereço:</label>
                <input type="text" name="endereco_usuario" value="<%= usuario.getEndereco() %>">
            </div>

            <div class="form-group">
                <label>Nova Senha (deixe em branco para não alterar):</label>
                <input type="password" name="senha_usuario">
            </div>

            <button type="submit">Salvar Alterações</button>
        </form>

        <div class="delete-section">
            <form action="<%= request.getContextPath() %>/account/remover" method="POST"
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
