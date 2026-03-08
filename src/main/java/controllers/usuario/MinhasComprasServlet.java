package controllers.usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.usuario.Usuario;
import models.venda.Venda;
import models.venda.VendaDAO;
import models.vendaProduto.VendaProduto;
import models.vendaProduto.VendaProdutoDAO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/account/compras")
public class MinhasComprasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

        VendaDAO vendaDAO = new VendaDAO();
        List<Venda> vendas = vendaDAO.obterVendaPorUsuario(usuario.getId_usuario());

        VendaProdutoDAO vendaProdutoDAO = new VendaProdutoDAO();
        Map<Integer, List<VendaProduto>> itensPorVenda = new HashMap<>();
        for (Venda v : vendas) {
            itensPorVenda.put(v.getIdVenda(), vendaProdutoDAO.obterItensPorVenda(v.getIdVenda()));
        }

        request.setAttribute("minhasVendas", vendas);
        request.setAttribute("itensPorVenda", itensPorVenda);

        request.getRequestDispatcher("/WEB-INF/jsp/account/compras.jsp")
                .forward(request, response);
    }
}
