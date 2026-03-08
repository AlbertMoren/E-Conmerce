<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="models.produto.Produto" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Papoco - Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

    <style>
        .product-card {
            transition: transform 0.2s, shadow 0.2s;
            border: none;
        }
        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.12) !important;
        }
        .img-container {
            height: 200px;
            overflow: hidden;
            background-color: #f8f9fa;
        }
        .product-img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .badge-category {
            font-size: 0.75rem;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
    </style>
</head>
<body class="bg-light">

<jsp:include page="cabecalho.jsp" />

<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="display-6 fw-bold text-dark">Nossa Vitrine</h1>
        <span class="badge bg-dark rounded-pill">Explorar tudo</span>
    </div>

    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 row-cols-xl-4 g-4">
        <%
            List<Produto> lista = (List<Produto>) request.getAttribute("produtos");
            if (lista != null && !lista.isEmpty()) {
                for (Produto p : lista) {
                    if(p.getQuantidade() <= 0){
                        continue;
                    }
        %>
        <div class="col">
            <div class="card h-100 shadow-sm product-card">
                <div class="img-container position-relative">
                    <img src="${pageContext.request.contextPath}/public/mostrarFotoProduto?id=<%= p.getId_produto() %>"
                         class="product-img card-img-top"
                         alt="<%= p.getDescricao() %>"
                         onerror="this.src='https://placehold.co/400x400?text=Sem+Imagem'">

                    <% if (p.getQuantidade() <= 5 && p.getQuantidade() > 0) { %>
                    <span class="position-absolute top-0 end-0 badge bg-danger m-2">
                            Últimas <%= p.getQuantidade() %> unidades!
                        </span>
                    <% } %>
                </div>

                <div class="card-body d-flex flex-column">
                    <div class="mb-1">
                        <span class="badge bg-secondary badge-category text-white">
                            <%= p.getCategoria() != null ? p.getCategoria().getNome() : "Geral" %>
                        </span>
                    </div>

                    <h5 class="card-title text-truncate fw-bold mb-3"><%= p.getDescricao() %></h5>

                    <div class="mt-auto">
                        <div class="d-flex align-items-center justify-content-between">
                            <span class="h5 text-primary fw-bold mb-0">R$ <%= String.format("%.2f", p.getPreco()) %></span>
                            <small class="text-muted"><%= p.getQuantidade() %> em estoque</small>
                        </div>
                    </div>
                </div>

                <div class="card-footer bg-white border-top-0 p-3">
                    <form action="${pageContext.request.contextPath}/carrinho/adicionar" method="post" class="m-0">
                        <input type="hidden" name="produtoId" value="<%= p.getId_produto() %>">
                        <input type="hidden" name="quantidade" value="1">
                        <button type="submit" class="btn btn-dark w-100 fw-bold py-2">
                            <i class="bi bi-cart-plus me-2"></i> Adicionar
                        </button>
                    </form>
                </div>
            </div>
        </div>
        <%
            }
        } else {
        %>
        <div class="col-12 text-center py-5">
            <i class="bi bi-search display-1 text-muted"></i>
            <p class="h4 mt-3 text-muted">Nenhum produto disponível no momento.</p>
        </div>
        <%
            }
        %>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>