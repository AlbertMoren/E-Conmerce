<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="models.produto.Produto" %>

<!DOCTYPE html>
<html>
<head>
    <title>Papoco - Home</title>
    <style>
        body { font-family: sans-serif; padding: 20px; }
        .produto-item {
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 10px;
            border-radius: 5px;
            background: #f9f9f9;
        }
        .preco { color: #FF4500; font-weight: bold; }
        .btn { background: black; color: white; padding: 5px 10px; border: none; cursor: pointer; }
    </style>
</head>
<body>

<jsp:include page="cabecalho.jsp" />

<h1>Lista de Produtos</h1>
<hr>

<%

    List<Produto> lista = (List<Produto>) request.getAttribute("produtos");


    if (lista != null && !lista.isEmpty()) {


        for (Produto p : lista) {
%>

<div class="produto-item">
    <h3> <%= p.getDescricao() %> </h3>

    <p>Categoria ID: <%= p.getCategoria() != null ? p.getCategoria().getNome() : "Sem categoria" %></p>

    <p class="preco">R$ <%= p.getPreco() %></p>

    <p>Estoque: <%= p.getQuantidade() %></p>


</div>

<%
    }
} else {
%>
<p>Nenhum produto encontrado no banco de dados.</p>
<%
    }
%>

</body>
</html>