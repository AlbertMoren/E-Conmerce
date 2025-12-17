package controllers;
import java.io.IOException;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import modelo.usuario.Usuario;
import models.Usuario;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FiltroSeguranca implements Filter {
    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {

    }

    @Override
    public String description() {
        return "";
    }
    /*
     * Classe que implementa o filtro de segurança de recursos da aplicação
     */
    public FiltroSeguranca(){
    }
    public void doFilter(javax.servlet.ServletRequest request, javax.servlet.ServletResponse response,
                         javax.servlet.FilterChain chain)
                        throws IOException, javax.servlet.ServletException{
        javax.servlet.http.HttpServletRequest req = (javax.servlet.http.HttpServletRequest) request;
        if (req.getRequestURI().startsWith(req.getContextPath()+ "/secure/")
            || req.getRequestURI().startsWith(req.getContextPath()+ "/admin/")){
            javax.servlet.http.HttpSession session = req.getSession();
            if (session.getAttribute("usuario") == null && session.getAttribute("usuario") instanceof models.Usuario){
                models.Usuario usuario = (models.Usuario) session.getAttribute("usuario");
                if (req.getRequestURI().startsWith(req.getContextPath()+ "/admin/") && usuario.getAdmini){}
            }
        }
    }

}
