<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="models.carrinho.CarrinhoItem" %>
<%@ page import="models.usuario.Usuario" %>

<jsp:include page="cabecalho.jsp" />

<%
    List<CarrinhoItem> itens = (List<CarrinhoItem>) request.getAttribute("carrinhoItens");
    BigDecimal total = (BigDecimal) request.getAttribute("carrinhoTotal");
    Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

    String msgErro = (String) session.getAttribute("mensagemErro");
    if (msgErro != null) session.removeAttribute("mensagemErro");

    String msgSucesso = (String) session.getAttribute("mensagemSucesso");
    if (msgSucesso != null) session.removeAttribute("mensagemSucesso");
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Papoco - Carrinho</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-4">

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1 class="h3 mb-0">Carrinho de Compras</h1>
        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/home">Continuar comprando</a>
    </div>

    <% if (msgErro != null) { %>
        <div class="alert alert-danger"><%= msgErro %></div>
    <% } %>
    <% if (msgSucesso != null) { %>
        <div class="alert alert-success"><%= msgSucesso %></div>
    <% } %>

    <div class="card shadow-sm">
        <div class="card-body">

            <% if (itens == null || itens.isEmpty()) { %>
                <p class="text-muted mb-0">Seu carrinho está vazio.</p>
            <% } else { %>

                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead>
                        <tr>
                            <th>Produto</th>
                            <th style="width: 160px;">Quantidade</th>
                            <th style="width: 140px;">Preço</th>
                            <th style="width: 140px;">Subtotal</th>
                            <th style="width: 120px;"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <% for (CarrinhoItem item : itens) {
                            if (item.getProduto() == null) continue;
                            double preco = item.getProduto().getPreco();
                            double subtotal = preco * item.getQuantidade();
                        %>
                            <tr>
                                <td>
                                    <strong><%= item.getProduto().getDescricao() %></strong>
                                </td>

                                <td>
                                    <form action="${pageContext.request.contextPath}/carrinho/atualizar" method="post" class="d-flex gap-2">
                                        <input type="hidden" name="produtoId" value="<%= item.getProduto().getId_produto() %>" />
                                        <input type="number" min="0" name="quantidade" value="<%= item.getQuantidade() %>" class="form-control form-control-sm" />
                                        <button class="btn btn-sm btn-primary" type="submit">Atualizar</button>
                                    </form>
                                </td>

                                <td>R$ <%= String.format("%.2f", preco) %></td>
                                <td>R$ <%= String.format("%.2f", subtotal) %></td>

                                <td>
                                    <form action="${pageContext.request.contextPath}/carrinho/remover" method="post" onsubmit="return confirm('Remover este item?');">
                                        <input type="hidden" name="produtoId" value="<%= item.getProduto().getId_produto() %>" />
                                        <button class="btn btn-sm btn-outline-danger" type="submit">Remover</button>
                                    </form>
                                </td>
                            </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>

                <div class="d-flex justify-content-end">
                    <div class="text-end">
                        <div class="h5">Total: R$ <%= (total != null ? String.format("%.2f", total.doubleValue()) : "0,00") %></div>

                        <% if (usuario != null) { %>
                            <form action="${pageContext.request.contextPath}/account/finalizar-compra" method="post" class="mt-2">
                                <button class="btn btn-success btn-lg" type="submit">Finalizar compra</button>
                            </form>
                        <% } else { %>
                            <a class="btn btn-success btn-lg mt-2" href="${pageContext.request.contextPath}/login">Faça login para finalizar</a>
                        <% } %>
                    </div>
                </div>
            <% } %>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
