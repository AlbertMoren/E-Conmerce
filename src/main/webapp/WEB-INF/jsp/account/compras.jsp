<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="models.venda.Venda" %>
<%@ page import="models.vendaProduto.VendaProduto" %>
<%@ page import="models.usuario.Usuario" %>

<jsp:include page="../public/cabecalho.jsp" />

<%
    Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
    List<Venda> vendas = (List<Venda>) request.getAttribute("minhasVendas");
    Map<Integer, List<VendaProduto>> itensPorVenda = (Map<Integer, List<VendaProduto>>) request.getAttribute("itensPorVenda");

    String msgSucesso = (String) session.getAttribute("mensagemSucesso");
    if (msgSucesso != null) session.removeAttribute("mensagemSucesso");

    String msgErro = (String) session.getAttribute("mensagemErro");
    if (msgErro != null) session.removeAttribute("mensagemErro");
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Minhas Compras</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1 class="h3 mb-0">Minhas Compras</h1>
        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/home">Voltar</a>
    </div>

    <% if (msgErro != null) { %>
        <div class="alert alert-danger"><%= msgErro %></div>
    <% } %>
    <% if (msgSucesso != null) { %>
        <div class="alert alert-success"><%= msgSucesso %></div>
    <% } %>

    <% if (usuario == null) { %>
        <div class="alert alert-warning">Você precisa estar logado.</div>
    <% } else if (vendas == null || vendas.isEmpty()) { %>
        <div class="alert alert-info">Você ainda não possui compras efetivadas.</div>
    <% } else { %>

        <div class="accordion" id="accordionCompras">
            <% for (Venda v : vendas) {
                String collapseId = "collapse" + v.getIdVenda();
                String headingId = "heading" + v.getIdVenda();
            %>
            <div class="accordion-item">
                <h2 class="accordion-header" id="<%= headingId %>">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#<%= collapseId %>">
                        Pedido #<%= v.getIdVenda() %> — <%= v.getDataHora() %> — Total: R$ <%= String.format("%.2f", v.getValorTotal().doubleValue()) %> — <%= v.getStatus() %>
                    </button>
                </h2>
                <div id="<%= collapseId %>" class="accordion-collapse collapse" data-bs-parent="#accordionCompras">
                    <div class="accordion-body">
                        <h6>Itens</h6>
                        <table class="table table-sm">
                            <thead>
                            <tr>
                                <th>Produto</th>
                                <th style="width: 100px;">Qtd</th>
                                <th style="width: 140px;">Preço</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                List<VendaProduto> itens = (itensPorVenda != null) ? itensPorVenda.get(v.getIdVenda()) : null;
                                if (itens != null) {
                                    for (VendaProduto vp : itens) {
                            %>
                            <tr>
                                <td><%= (vp.getProduto() != null) ? vp.getProduto().getDescricao() : "-" %></td>
                                <td><%= vp.getQuantidade() %></td>
                                <td>R$ <%= String.format("%.2f", vp.getPrecoUnitario().doubleValue()) %></td>
                            </tr>
                            <%      }
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <% } %>
        </div>

    <% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
