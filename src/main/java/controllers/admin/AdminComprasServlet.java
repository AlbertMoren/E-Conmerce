package controllers.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.venda.Venda;
import models.venda.VendaDAO;
import models.vendaProduto.VendaProduto;
import models.vendaProduto.VendaProdutoDAO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/compras")
public class AdminComprasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        VendaDAO vendaDAO = new VendaDAO();
        List<Venda> vendas = vendaDAO.obterTodos();

        VendaProdutoDAO vendaProdutoDAO = new VendaProdutoDAO();
        Map<Integer, List<VendaProduto>> itensPorVenda = new HashMap<>();
        for (Venda v : vendas) {
            itensPorVenda.put(v.getIdVenda(), vendaProdutoDAO.obterItensPorVenda(v.getIdVenda()));
        }

        request.setAttribute("todasVendas", vendas);
        request.setAttribute("itensPorVenda", itensPorVenda);

        request.getRequestDispatcher("/WEB-INF/jsp/admin/compras.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        if (!"remover".equals(acao)) {
            response.sendRedirect(request.getContextPath() + "/admin/compras");
            return;
        }

        int idVenda = Integer.parseInt(request.getParameter("id_venda"));

        VendaDAO vendaDAO = new VendaDAO();
        boolean sucesso = vendaDAO.removerComItens(idVenda);

        if (sucesso) {
            request.getSession().setAttribute("mensagemSucesso", "Compra removida com sucesso.");
        } else {
            request.getSession().setAttribute("mensagemErro", "Falha ao remover a compra.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/compras");
    }
}
