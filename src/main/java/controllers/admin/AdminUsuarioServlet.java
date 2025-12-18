package controllers.admin;

import models.usuario.Usuario;
import models.usuario.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/usuarios")
public class AdminUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> listaUsuarios = usuarioDAO.obterTodos();

        request.setAttribute("listaDeUsuarios", listaUsuarios);

        request.getRequestDispatcher("/WEB-INF/jsp/admin/usuarios.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        String emailParaRemover = request.getParameter("email_para_remover");

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuarioASerRemovido = dao.obter(emailParaRemover);

        if (usuarioASerRemovido != null && usuarioASerRemovido.getAdministrador()) {
            //request.setAttribute("mensagemErro", "Contas de administrador não podem ser removidas pelo painel de administrador.");
            //request.getRequestDispatcher("/admin/usuarios").forward(request, response);

            sessao.setAttribute("mensagemErro", "Contas de administrador não podem ser removidas pelo painel.");
            response.sendRedirect(request.getContextPath() + "/admin/usuarios");
            return;
        } else {
            boolean sucesso = UsuarioDAO.remover(emailParaRemover);
            if (sucesso) {
                sessao.setAttribute("mensagemSucesso", "Usuário removido!");
            }

            response.sendRedirect(request.getContextPath() + "/admin/usuarios?msg=removido");
        }
    }
}
