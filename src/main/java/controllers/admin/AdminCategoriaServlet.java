package controllers.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.categoria.Categoria;
import models.categoria.CategoriaDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/categorias")
public class AdminCategoriaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoriaDAO categoria = new CategoriaDAO();
        List<Categoria> listaCategorias = categoria.obterTodos();

        request.setAttribute("listaDeCategorias", listaCategorias);

        request.getRequestDispatcher("/WEB-INF/jsp/admin/categorias.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        String acao = request.getParameter("acao");
        boolean sucesso = false;

        try {
            if ("remover".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                sucesso = categoriaDAO.remover(id);

            } else if ("cadastrar".equals(acao)) {
                String nome = request.getParameter("nome");
                sucesso = categoriaDAO.inserir(nome);

            } else if ("atualizar".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nome = request.getParameter("nome");
                sucesso = categoriaDAO.atualizar(id, nome);
            }
        } catch (Exception e) {
            sucesso = false;
            e.printStackTrace();
        }

        String tipomsg = sucesso ? "mensagemSucesso" : "mensagemErro";
        String msg = sucesso ? "Operação de " + acao + " categoria realizada com sucesso!" : "Erro ao realizar " + acao + " categoria!";
        request.getSession().setAttribute(tipomsg, msg);
        response.sendRedirect(request.getContextPath() + "/admin/categorias");
    }
}
