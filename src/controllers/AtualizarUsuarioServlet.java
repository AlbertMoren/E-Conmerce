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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/logar");
            return;
        }

        request.getRequestDispatcher("/dashboardClient.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/logar");
            return;
        }


        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

        String emailAntigo = usuarioLogado.getEmail();

        // dados do formulário
        String nomeForm = request.getParameter("nome_usuario");
        String emailForm = request.getParameter("email_usuario");
        String enderecoForm = request.getParameter("endereco_usuario");
        String senhaForm = request.getParameter("senha_usuario");

        // mantém valores antigos se vier vazio
        if (nomeForm == null || nomeForm.trim().isEmpty())
            nomeForm = usuarioLogado.getNome();

        if (emailForm == null || emailForm.trim().isEmpty())
            emailForm = usuarioLogado.getEmail();

        if (enderecoForm == null || enderecoForm.trim().isEmpty())
            enderecoForm = usuarioLogado.getEndereco();

        if (senhaForm == null || senhaForm.isEmpty())
            senhaForm = usuarioLogado.getSenha();

        boolean sucesso = UsuarioDAO.atualizar(
                nomeForm,
                emailForm,
                enderecoForm,
                senhaForm,
                emailAntigo
        );

        if (sucesso) {

            usuarioLogado.setNome(nomeForm);
            usuarioLogado.setEmail(emailForm);
            usuarioLogado.setEndereco(enderecoForm);
            usuarioLogado.setSenha(senhaForm);

            sessao.setAttribute("usuarioLogado", usuarioLogado);

            request.setAttribute("mensagemSucesso", "Dados atualizados com sucesso!");
        } else {
            request.setAttribute("mensagemErro", "Erro ao atualizar. O novo e-mail pode já estar em uso.");
        }

        request.getRequestDispatcher("/dashboardClient.jsp")
                .forward(request, response);
    }
}
