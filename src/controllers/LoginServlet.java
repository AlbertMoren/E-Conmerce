package controllers;

import models.UsuarioDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;


// webservlet serve pra conectar a url ao código
@WebServlet("/logar")
public class LoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/login.jsp")
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
            sessao.setAttribute("isAdmin", usuario.getAdministrador());

            if (usuario.getAdministrador()) {
                response.sendRedirect(request.getContextPath() + "/admin/painel");
            } else {
                response.sendRedirect(request.getContextPath() + "/inicio");
            }

        } else {
            request.setAttribute("mensagemErro", "E-mail ou senha inválidos.");
            request.getRequestDispatcher("/login.jsp")
                    .forward(request, response);
        }
    }
}
