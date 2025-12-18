package config.access;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import models.usuario.Usuario;

public class SegurancaFilter implements Filter {

    public SegurancaFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getRequestURI().startsWith(req.getContextPath() + "/account/")
                || req.getRequestURI().startsWith(req.getContextPath() + "/admin/")) {
            HttpSession session = req.getSession();
            if (session.getAttribute("usuario") != null && session.getAttribute("usuario") instanceof Usuario) {
                Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
                if (req.getRequestURI().startsWith(req.getContextPath() + "/admin/") && usuario.getAdministrador() == true) {
                    chain.doFilter(req, response);
                } else {
                    if (req.getRequestURI().startsWith(req.getContextPath() + "/account/") && usuario != null) {
                        chain.doFilter(req, response);
                    } else {
                        request.setAttribute("mensagem", "Você não tem permissão para acesso este recurso");
                        request.getRequestDispatcher("/home").forward(request, response);
                    }
                }
            } else {
                request.setAttribute("mensagem", "Você não tem permissão para acesso este recurso");
                request.getRequestDispatcher("/home").forward(request, response);
            }
        } else {
            chain.doFilter(req, response);
        }
    }
}
