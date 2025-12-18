package config.access;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.usuario.Usuario;
import models.usuario.UsuarioDAO;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.obter(login);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", usuario);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/principal.jsp");
            requestDispatcher.forward(request, response);
        } else {
            request.setAttribute("mensagem", "Login ou senha incorreta");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("Inicio");
            requestDispatcher.forward(request, response);
        }
    }
}