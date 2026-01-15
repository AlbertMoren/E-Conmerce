package controllers.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.categoria.Categoria;
import models.categoria.CategoriaDAO;
import models.produto.Produto;
import models.produto.ProdutoDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/produtos")
@jakarta.servlet.annotation.MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 15    // 15 MB
)
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
                System.out.println("Cadastrando produto");
                String descricao = request.getParameter("descricao");
                Double preco = Double.parseDouble(request.getParameter("preco"));
                Part foto = request.getPart("foto");
                int quantidade = Integer.parseInt(request.getParameter("quantidade"));
                int idCategoria = Integer.parseInt(request.getParameter("id_categoria"));

                sucesso = produtoDAO.inserir(descricao, preco,foto, quantidade, idCategoria);
                System.out.println(sucesso);

            } else if ("atualizar".equals(acao)) {
                System.out.println("Atualizando produto");
                int id = Integer.parseInt(request.getParameter("id"));
                String descricao = request.getParameter("descricao");
                Double preco = Double.parseDouble(request.getParameter("preco"));
                Part foto = request.getPart("foto");
                int quantidade = Integer.parseInt(request.getParameter("quantidade"));
                int idCategoria = Integer.parseInt(request.getParameter("id_categoria"));

                sucesso = produtoDAO.atualizar(id, descricao, preco,foto, quantidade, idCategoria);
            }
        } catch (Exception e) {
            sucesso = false;
            e.printStackTrace();
        }

        String tipomsg = sucesso ? "mensagemSucesso" : "mensagemErro";
        String msg = sucesso ? "Operação de " + acao + " produto realizada com sucesso!" : "Erro ao realizar " + acao + " produto!";
        request.getSession().setAttribute(tipomsg, msg);
        response.sendRedirect(request.getContextPath() + "/admin/produtos");
    }
}