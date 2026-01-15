package controllers.usuario;

import models.usuario.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import jakarta.servlet.http.HttpSession;

// webservlet serve pra conectar a url ao código
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/public/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email_usuario");
        String senha = request.getParameter("senha_usuario");

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuarioDAO.validarLogin(email, senha)) {

            var usuario = usuarioDAO.obter(email);

            HttpSession sessao = request.getSession(true);
            sessao.setAttribute("usuarioLogado", usuario);

            if (usuario.getAdministrador()) {
                request.getRequestDispatcher("admin/usuarios")
                        .forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/jsp/account/dashboardClient.jsp")
                        .forward(request, response);
            }

        } else {
            request.setAttribute("mensagemErro", "Email ou senha inválidos.");
            request.getRequestDispatcher("/WEB-INF/jsp/public/login.jsp")
                    .forward(request, response);
        }
    }
}

