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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // pega os dados preenchidos no login
        String email = request.getParameter("email_usuario");
        String senha = request.getParameter("senha_usuario");

        // chama o dao
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean loginValido = usuarioDAO.validarLogin(email, senha);


        if (loginValido) {

            HttpSession sessao = request.getSession(true);
            sessao.setAttribute("usuarioLogado", email);
            response.sendRedirect("dashboard.jsp");

        } else {
            request.setAttribute("mensagemErro", "E-mail ou senha inválidos.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }


    }
}