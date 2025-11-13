package controllers;

import models.Usuario;
import models.UsuarioDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/painel")
public class ListarUsuariosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        // Bloco de Segurança
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Boolean isAdmin = (Boolean) sessao.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("dashboardClient.jsp");
            return;
        }

        // busca dados no banco de dados
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> listaUsuarios = usuarioDAO.obterTodos();

        // coloca os dados no request
        request.setAttribute("listaDeUsuarios", listaUsuarios);

        // 4. encaminha para o jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboardAdmin.jsp");
        dispatcher.forward(request, response);
    }
}