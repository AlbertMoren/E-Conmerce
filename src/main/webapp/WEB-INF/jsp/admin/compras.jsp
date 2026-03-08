<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="models.venda.Venda" %>
<%@ page import="models.vendaProduto.VendaProduto" %>

<%@include file="cabecalhoAdmin.jsp" %>
<%@include file="menuLateral.jsp" %>

<%
    List<Venda> vendas = (List<Venda>) request.getAttribute("todasVendas");
    Map<Integer, List<VendaProduto>> itensPorVenda = (Map<Integer, List<VendaProduto>>) request.getAttribute("itensPorVenda");

    String erro = (String) session.getAttribute("mensagemErro");
    if (erro != null) session.removeAttribute("mensagemErro");

    String sucesso = (String) session.getAttribute("mensagemSucesso");
    if (sucesso != null) session.removeAttribute("mensagemSucesso");
%>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<main class="container-fluid py-4">

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2 class="h3 mb-0">Compras (todas)</h2>
        <span class="badge bg-primary rounded-pill"><%= (vendas != null) ? vendas.size() : 0 %> pedidos</span>
    </div>

    <% if (erro != null) { %>
        <div class="alert alert-danger"><%= erro %></div>
    <% } %>
    <% if (sucesso != null) { %>
        <div class="alert alert-success"><%= sucesso %></div>
    <% } %>

    <% if (vendas == null || vendas.isEmpty()) { %>
        <div class="alert alert-info">Nenhuma compra encontrada.</div>
    <% } else { %>

        <div class="accordion" id="accordionAdminCompras">
            <% for (Venda v : vendas) {
                String collapseId = "collapse" + v.getIdVenda();
                String headingId = "heading" + v.getIdVenda();
            %>
            <div class="accordion-item">
                <h2 class="accordion-header" id="<%= headingId %>">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#<%= collapseId %>">
                        Pedido #<%= v.getIdVenda() %> — Cliente: <%= (v.getUsuario() != null ? v.getUsuario().getEmail() : "-") %> — Total: R$ <%= String.format("%.2f", v.getValorTotal().doubleValue()) %> — <%= v.getStatus() %>
                    </button>
                </h2>
                <div id="<%= collapseId %>" class="accordion-collapse collapse" data-bs-parent="#accordionAdminCompras">
                    <div class="accordion-body">

                        <div class="d-flex justify-content-between align-items-center mb-2">
                            <h6 class="mb-0">Itens</h6>
                            <form action="${pageContext.request.contextPath}/admin/compras" method="post" onsubmit="return confirm('Excluir esta compra? Esta ação não pode ser desfeita.');" class="m-0">
                                <input type="hidden" name="acao" value="remover" />
                                <input type="hidden" name="id_venda" value="<%= v.getIdVenda() %>" />
                                <button class="btn btn-sm btn-outline-danger" type="submit">Excluir compra</button>
                            </form>
                        </div>

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
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<%@include file="rodapeAdmin.jsp" %>
