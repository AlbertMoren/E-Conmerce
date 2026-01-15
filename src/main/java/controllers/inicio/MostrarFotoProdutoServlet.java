package controllers.inicio;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import models.produto.Produto;
import models.produto.ProdutoDAO;

import java.io.IOException;

@WebServlet("/public/mostrarFotoProduto")
public class MostrarFotoProdutoServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProdutoDAO produtoDao = new ProdutoDAO();
        Produto produto = produtoDao.obter(id);
        File diretorio = new File("/home/mslms/Projects/SMD/E-Conmerce/pictures/");
        File foto = new File(diretorio, produto.getFoto());
        String mimeType = getServletContext().getMimeType(foto.getName());
        response.setContentType(mimeType);
        response.setContentLengthLong(foto.length());
        try( FileInputStream fis = new FileInputStream(foto); OutputStream out = response.getOutputStream()){
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1){
                out.write(buffer,0, bytesRead);
            }
        }

    }
}