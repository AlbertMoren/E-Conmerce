<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="models.usuario.Usuario" %>

<style>

    .site-header {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 65px;
        background-color: #ff4b00;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0 40px;
        box-sizing: border-box;
        z-index: 1000;
        box-shadow: 0 2px 8px rgba(0,0,0,0.15);
    }

    .site-header .logo {
        font-size: 22px;
        font-weight: bold;
        color: white;
    }

    .site-header .nav-links {
        display: flex;
        align-items: center;
        gap: 20px;
        font-size: 15px;
    }

    .site-header .nav-links a {
        color: white;
        text-decoration: none;
        font-weight: 600;
        transition: opacity 0.2s;
    }

    .site-header .nav-links a:hover {
        opacity: 0.8;
        text-decoration: underline;
    }

    .site-header .user-name {
        color: white;
        font-weight: 500;
    }
    .header-spacer {
        height: 65px;
    }
</style>

<%
    Usuario usuario = null;
    if (session != null) {
        usuario = (Usuario) session.getAttribute("usuarioLogado");
    }
%>

<header class="site-header">
    <div class="logo">
        <a href="<%= request.getContextPath() %>/home">
            <img src="${pageContext.request.contextPath}/assets/LOGO-ICON.png" alt="Logo Papoco" class="logo">
        </a>
    </div>

    <nav class="nav-links">
        <% if (usuario == null) { %>
        <a href="<%= request.getContextPath() %>/login">Fazer Login</a>
        <% } else { %>
        <span class="user-name">Olá, <%= usuario.getNome() %></span>
        <a href="<%= request.getContextPath() %>/account/update">Minha conta</a>
        <a href="<%= request.getContextPath() %>/logout">Sair</a>
        <% } %>
    </nav>
</header>

<div class="header-spacer"></div>