package controllers.carrinho;

import config.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.carrinho.CarrinhoCompras;
import utils.Utils;

import java.io.IOException;

public class RemoverItemCarrinhoServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        int produtoId = Integer.parseInt(request.getParameter("produtoId"));

        Cookie cookie = Utils.obterCookieCarrrinhoCompras(request);
        if (cookie == null) {
            cookie = new Cookie(Constants.COOKIE_CARRINHO_COMPRAS_CHAVE, "");
        }

        CarrinhoCompras.removerItem(produtoId, cookie);
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);

        request.getRequestDispatcher("/home")
                .forward(request,response);

    }
}
