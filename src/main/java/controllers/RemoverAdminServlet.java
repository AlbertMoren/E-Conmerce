package controllers;

import models.usuario.Usuario;
import models.usuario.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/removerAdmin")
public class RemoverAdminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        // bloco de segurança
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp"); // Não tá logado
            return;
        }

        Boolean isAdmin = (Boolean) sessao.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("dashboardClient.jsp"); // Não é admin
            return;
        }

        // pega os dados
        // O email do admin da sessão
        String emailDoAdminLogado = (String) sessao.getAttribute("usuarioLogado");
        // O email do usuario quer remover
        String emailParaRemover = request.getParameter("email_para_remover");

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuarioASerRemovido = dao.obter(emailParaRemover);

        // impedir remoção de Admin
        if (usuarioASerRemovido != null && usuarioASerRemovido.getAdministrador()) {


            request.setAttribute("mensagemErro", "Contas de administrador não podem ser removidas pelo painel de administrador.");


            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
            return;
        }

        boolean sucesso = UsuarioDAO.remover(emailParaRemover);

        if(sucesso) {
             sessao.setAttribute("mensagemSucesso", "Usuário removido!");
        }

        response.sendRedirect("admin/painel");
    }
}