package controllers;

import models.UsuarioDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// webservlet serve pra conectar a url ao código

@WebServlet("/cadastrar")
public class CadastroServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // pega os dados preenchidos no cadastro
        String nome = request.getParameter("nome_usuario");
        String email = request.getParameter("email_usuario");
        String senha = request.getParameter("senha_usuario");

       // chama o dao
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean sucesso = usuarioDAO.inserir(nome, email, senha);

        // manda pra proxima tela
        response.setContentType("text/html;charset=UTF-8");
        if (sucesso) {
            response.sendRedirect("sucesso.html");
        } else {
            response.sendRedirect("erro.html");
        }
    }
}