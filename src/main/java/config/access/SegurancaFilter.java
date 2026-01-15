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

@jakarta.servlet.annotation.WebFilter("/*")
public class SegurancaFilter implements Filter {

    public SegurancaFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getRequestURI().endsWith("favicon.ico")
                || req.getRequestURI().contains("/assets/")
                || req.getRequestURI().contains("/public/")) {
            chain.doFilter(request, response);
            return;
        }

        if (req.getRequestURI().startsWith(req.getContextPath() + "/account/")
                || req.getRequestURI().startsWith(req.getContextPath() + "/admin/")) {
            HttpSession session = req.getSession();

            if (req.getRequestURI().startsWith(req.getContextPath() + "/account/update")) {
                Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
                if (usuario != null && usuario.getAdministrador()) {
                    request.setAttribute("mensagemErro", "Administradores não podem editar perfis de clientes por aqui.");
                    request.getRequestDispatcher("/WEB-INF/jsp/admin/usuarios").forward(request, response);
                    return;
                }
            }

            if (session.getAttribute("usuarioLogado") != null && session.getAttribute("usuarioLogado") instanceof Usuario) {
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
