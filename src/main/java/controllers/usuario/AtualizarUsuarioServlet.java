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

@WebServlet("/account/update")
public class AtualizarUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/account/dashboardClient.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

        String emailAntigo = usuarioLogado.getEmail();

        // dados do formulario
        String nomeForm = request.getParameter("nome_usuario");
        String emailForm = request.getParameter("email_usuario");
        String enderecoForm = request.getParameter("endereco_usuario");
        String senhaForm = request.getParameter("senha_usuario");

        // mantem valores antigos se vier vazio
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

            sessao.setAttribute("usuarioLogado", usuarioLogado); // Devolve para a sessão

            request.setAttribute("mensagemSucesso", "Dados atualizados com sucesso!");
        } else {
            request.setAttribute("mensagemErro", "Erro ao atualizar. O novo e-mail pode já estar em uso.");
        }

        request.getRequestDispatcher("/WEB-INF/jsp/account/dashboardClient.jsp")
                .forward(request, response);
    }
}
