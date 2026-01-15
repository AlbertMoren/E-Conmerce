package controllers.carrinho;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.carrinho.CarrinhoCompras;
import models.carrinho.CarrinhoItem;
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

        BigDecimal total = BigDecimal.ZERO;
        for (CarrinhoItem item : itens) {
            if (item.getProduto() == null) {
                continue;
            }
            BigDecimal preco = BigDecimal.valueOf(item.getProduto().getPreco());
            BigDecimal subtotal = preco.multiply(BigDecimal.valueOf(item.getQuantidade()));
            total = total.add(subtotal);
        }

        request.setAttribute("carrinhoItens", itens);
        request.setAttribute("carrinhoTotal", total);

        request.getRequestDispatcher("/WEB-INF/jsp/public/carrinho.jsp")
                .forward(request, response);
    }
}
