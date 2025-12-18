package controllers.usuario;

import models.usuario.Usuario;
import models.usuario.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
