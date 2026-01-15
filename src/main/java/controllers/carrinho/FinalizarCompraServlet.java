package controllers.carrinho;

import config.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.carrinho.CarrinhoCompras;
import models.carrinho.CarrinhoItem;
import models.usuario.Usuario;
import models.venda.VendaDAO;
import utils.Utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/account/finalizar-compra")
public class FinalizarCompraServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);
        Usuario usuario = (sessao != null) ? (Usuario) sessao.getAttribute("usuarioLogado") : null;

        if (usuario == null) {
            request.setAttribute("mensagemErro", "Você precisa estar logado para finalizar a compra.");
            request.getRequestDispatcher("/login").forward(request, response);
            return;
        }

        Cookie cookie = Utils.obterCookieCarrrinhoCompras(request);
        List<CarrinhoItem> itens = CarrinhoCompras.obter(cookie);

        if (itens == null || itens.isEmpty()) {
            request.getSession().setAttribute("mensagemErro", "Seu carrinho está vazio.");
            response.sendRedirect(request.getContextPath() + "/carrinho");
            return;
        }

        BigDecimal total = BigDecimal.ZERO;
        for (CarrinhoItem item : itens) {
            if (item.getProduto() == null) {
                continue;
            }
            BigDecimal preco = BigDecimal.valueOf(item.getProduto().getPreco());
            total = total.add(preco.multiply(BigDecimal.valueOf(item.getQuantidade())));
        }

        VendaDAO vendaDAO = new VendaDAO();
        Integer idVenda = vendaDAO.inserirComItens(usuario.getId_usuario(), total, "EFETIVADA", itens);

        if (idVenda == null) {
            request.getSession().setAttribute("mensagemErro", "Não foi possível efetivar sua compra. Tente novamente.");
            response.sendRedirect(request.getContextPath() + "/carrinho");
            return;
        }

        // Limpa o carrinho
        Cookie limpar = new Cookie(Constants.COOKIE_CARRINHO_COMPRAS_CHAVE, "");
        limpar.setMaxAge(0);
        String path = request.getContextPath();
        limpar.setPath((path == null || path.isEmpty()) ? "/" : path);
        response.addCookie(limpar);

        request.getSession().setAttribute("mensagemSucesso", "Compra efetivada com sucesso! Pedido #" + idVenda);
        response.sendRedirect(request.getContextPath() + "/account/compras");
    }
}
