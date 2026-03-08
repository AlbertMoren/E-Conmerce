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

@WebServlet("/account/remover")
public class RemoverUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

        boolean sucesso = UsuarioDAO.remover(usuarioLogado.getEmail());

        if (sucesso) {
            sessao.invalidate();
            response.sendRedirect( request.getContextPath() + "/login?msg=conta_removida");
        } else {
            request.setAttribute("mensagemErro", "Erro ao remover a conta.");
            request.getRequestDispatcher("/WEB-INF/jsp/account/dashboardClient.jsp")
                    .forward(request, response);
        }
    }
}
