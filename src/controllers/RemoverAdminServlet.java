package controllers;

import models.Usuario;
import models.UsuarioDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
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

            // Se o usuário logado for ADMIN, ele não pode se auto-remover por aqui.
            request.setAttribute("mensagemErro", "Contas de administrador não podem ser removidas pelo painel de administrador.");

            // Retorna para o dashboard do cliente com a mensagem de erro
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