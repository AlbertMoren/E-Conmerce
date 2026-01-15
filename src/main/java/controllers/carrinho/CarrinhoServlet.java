package controllers.carrinho;

import config.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.carrinho.CarrinhoCompras;
import models.carrinho.CarrinhoItem;
import models.produto.Produto;
import models.produto.ProdutoDAO;
import utils.Utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        Cookie cookie = Utils.obterCookieCarrrinhoCompras(request);
        List<CarrinhoItem> itens = CarrinhoCompras.obter(cookie);

        boolean alteracao = false;
        BigDecimal total = BigDecimal.ZERO;
        for (CarrinhoItem item : itens) {
            if (item.getProduto() == null) {
                continue;
            }

            int estoqueProduto =  item.getProduto().getQuantidade();
            int quantidadeCarrinho = item.getQuantidade();
            if (estoqueProduto < quantidadeCarrinho){
                quantidadeCarrinho = estoqueProduto;

                int produtoId = item.getProduto().getId_produto();
                CarrinhoCompras.atualizarQuantidade(produtoId, quantidadeCarrinho, cookie);
                alteracao = true;
            }

            BigDecimal preco = BigDecimal.valueOf(item.getProduto().getPreco());
            BigDecimal subtotal = preco.multiply(BigDecimal.valueOf(quantidadeCarrinho));
            total = total.add(subtotal);
        }

        if (alteracao) {
            request.getSession().setAttribute("mensagemErro", "Quantidade inválida de um determinado produto no Carrinho.");

            cookie.setMaxAge(Integer.MAX_VALUE);
            String path = request.getContextPath();
            cookie.setPath((path == null || path.isEmpty()) ? "/" : path);
            response.addCookie(cookie);

            itens = CarrinhoCompras.obter(cookie);
        }

        request.setAttribute("carrinhoItens", itens);
        request.setAttribute("carrinhoTotal", total);

        request.getRequestDispatcher("/WEB-INF/jsp/public/carrinho.jsp")
                .forward(request, response);
    }
}
