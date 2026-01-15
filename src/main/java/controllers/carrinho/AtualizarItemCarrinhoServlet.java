package controllers.carrinho;

import config.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.carrinho.CarrinhoCompras;
import utils.Utils;

import java.io.IOException;

@WebServlet("/carrinho/atualizar")
public class AtualizarItemCarrinhoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int produtoId = Integer.parseInt(request.getParameter("produtoId"));
        int quantidade = Integer.parseInt(request.getParameter("quantidade"));

        Cookie cookie = Utils.obterCookieCarrrinhoCompras(request);
        if (cookie == null) {
            cookie = new Cookie(Constants.COOKIE_CARRINHO_COMPRAS_CHAVE, "");
        }

        CarrinhoCompras.atualizarQuantidade(produtoId, quantidade, cookie);

        cookie.setMaxAge(Integer.MAX_VALUE);
        String path = request.getContextPath();
        cookie.setPath((path == null || path.isEmpty()) ? "/" : path);
        response.addCookie(cookie);

        response.sendRedirect(request.getContextPath() + "/carrinho");
    }
}
