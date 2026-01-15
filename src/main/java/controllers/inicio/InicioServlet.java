package controllers.inicio;

import models.produto.Produto;
import models.produto.ProdutoDAO;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/home")
public class InicioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Produto> listaDeProdutos = ProdutoDAO.obterTodos();

        request.setAttribute("produtos", listaDeProdutos);

        request.getRequestDispatcher("/WEB-INF/jsp/public/home.jsp")
                .forward(request, response);
    }
}