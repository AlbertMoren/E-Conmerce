package controllers;
import models.Usuario;
import models.UsuarioDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;


// webservlet serve pra conectar a url ao código

@WebServlet("/remover")
public class RemoverUsuarioServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(true);
        String emailDoUsuarioLogado = (String) sessao.getAttribute("usuarioLogado");

        boolean sucesso = UsuarioDAO.remover(emailDoUsuarioLogado);

        if(sucesso) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }else{
            request.setAttribute("mensagemErro", "Erro ao atualizar.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }

    }
}