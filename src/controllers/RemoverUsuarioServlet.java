package controllers;

import models.Usuario;
import models.UsuarioDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/remover")
public class RemoverUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        // Verifica se existe sessão e usuário logado
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

        boolean sucesso = UsuarioDAO.remover(usuarioLogado.getEmail());

        if (sucesso) {
            // invalida a sessão após remover
            sessao.invalidate();
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("mensagemErro", "Erro ao remover a conta.");
            request.getRequestDispatcher("/dashboardClient.jsp")
                    .forward(request, response);
        }
    }
}
