package controllers.usuario;

import models.usuario.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cadastrar")
public class CadastroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/public/cadastro.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome_usuario");
        String email = request.getParameter("email_usuario");
        String senha = request.getParameter("senha_usuario");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean sucesso = usuarioDAO.inserir(nome, email, senha);

        if (sucesso) {
            request.setAttribute("mensagemSucesso", "Usuário cadastrado com sucesso!");
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("mensagemErro", "Erro ao cadastrar. O e-mail pode já estar em uso.");
            request.getRequestDispatcher("/WEB-INF/jsp/public/cadastro.jsp")
                    .forward(request, response);
        }

    }
}