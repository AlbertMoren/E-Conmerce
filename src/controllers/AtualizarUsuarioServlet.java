package controllers;

import models.Usuario;
import models.UsuarioDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/atualizar")
public class AtualizarUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false); // Pega a sessão

        // Verifica se o usuário esta logado
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String emailLogado = (String) sessao.getAttribute("usuarioLogado");

        // pega os dados do usuario logado
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuarioAtual = dao.obter(emailLogado);

        if (usuarioAtual == null) {
            // Se o usuário foi deletado por outro admin, por exemplo
            sessao.invalidate();
            response.sendRedirect("login.jsp");
            return;
        }

        // pega os dados novos do formulario
        String nomeForm = request.getParameter("nome_usuario");
        String emailForm = request.getParameter("email_usuario");
        String enderecoForm = request.getParameter("endereco_usuario");
        String senhaForm = request.getParameter("senha_usuario");

        // Se o campo do formulario veio vazio, usa o valor antigo do banco

        if (nomeForm == null || nomeForm.trim().isEmpty()) {
            nomeForm = usuarioAtual.getNome();
        }

        if (emailForm == null || emailForm.trim().isEmpty()) {
            emailForm = usuarioAtual.getEmail();
        }

        if (enderecoForm == null || enderecoForm.trim().isEmpty()) {
            enderecoForm = usuarioAtual.getEndereco();
        }

        if (senhaForm == null || senhaForm.isEmpty()) {
            senhaForm = usuarioAtual.getSenha();
        }

        boolean sucesso = UsuarioDAO.atualizar(nomeForm, emailForm, enderecoForm, senhaForm, emailLogado);

        if(sucesso) {
            // Se o email mudou, atualiza o email na sessão
            sessao.setAttribute("usuarioLogado", emailForm);
            request.setAttribute("mensagemSucesso", "Dados atualizados com sucesso!");
        } else {
            request.setAttribute("mensagemErro", "Erro ao atualizar. O novo e-mail pode já estar em uso.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboardClient.jsp");
        dispatcher.forward(request, response);
    }
}