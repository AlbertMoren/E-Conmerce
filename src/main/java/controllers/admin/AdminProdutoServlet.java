
package controllers.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.categoria.Categoria;
import models.categoria.CategoriaDAO;
import models.produto.Produto;
import models.produto.ProdutoDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/produtos")
public class AdminProdutoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Produto> listaProdutos = ProdutoDAO.obterTodos();

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<Categoria> listaCategorias = categoriaDAO.obterTodos();

        request.setAttribute("listaDeProdutos", listaProdutos);
        request.setAttribute("listaDeCategorias", listaCategorias);

        request.getRequestDispatcher("/WEB-INF/jsp/admin/produtos.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProdutoDAO produtoDAO = new ProdutoDAO();
        String acao = request.getParameter("acao");
        boolean sucesso = false;

        try {
            if ("remover".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                sucesso = ProdutoDAO.remover(id);

            } else if ("cadastrar".equals(acao)) {
                String descricao = request.getParameter("descricao");
                Double preco = Double.parseDouble(request.getParameter("preco"));
                int quantidade = Integer.parseInt(request.getParameter("quantidade"));
                int idCategoria = Integer.parseInt(request.getParameter("id_categoria"));

                sucesso = produtoDAO.inserir(descricao, preco, quantidade, idCategoria);

            } else if ("atualizar".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String descricao = request.getParameter("descricao");
                Double preco = Double.parseDouble(request.getParameter("preco"));
                int quantidade = Integer.parseInt(request.getParameter("quantidade"));
                int idCategoria = Integer.parseInt(request.getParameter("id_categoria"));

                sucesso = produtoDAO.atualizar(id, descricao, preco, quantidade, idCategoria);
            }
        } catch (NumberFormatException | NullPointerException ignored) { }

        String msg = sucesso ? "Operação de " + acao + " realizada com sucesso!" : "Erro ao realizar " + acao;
        request.getSession().setAttribute("mensagem", msg);
        response.sendRedirect(request.getContextPath() + "/admin/produtos");
    }
}