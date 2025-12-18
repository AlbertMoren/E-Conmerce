package controllers;

import models.usuario.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;


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

        if (sucesso) {

            request.setAttribute("mensagemSucesso", "Usuário cadastrado com sucesso!");


        } else {

            request.setAttribute("mensagemErro", "Erro ao cadastrar. O e-mail pode já estar em uso.");


        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastro.jsp");
        dispatcher.forward(request, response);
    }
}