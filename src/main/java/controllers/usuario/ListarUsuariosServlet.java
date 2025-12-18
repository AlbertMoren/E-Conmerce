package controllers.usuario;

import models.usuario.Usuario;
import models.usuario.UsuarioDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/painel")
public class ListarUsuariosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        // Bloco de Segurança
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Boolean isAdmin = (Boolean) sessao.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("dashboardClient.jsp");
            return;
        }

        // busca dados no banco de dados
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> listaUsuarios = usuarioDAO.obterTodos();

        // coloca os dados no request
        request.setAttribute("listaDeUsuarios", listaUsuarios);

        // 4. encaminha para o jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboardAdmin.jsp");
        dispatcher.forward(request, response);
    }
}