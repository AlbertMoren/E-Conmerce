package controllers.produto;

import jakarta.servlet.annotation.WebServlet;
import models.categoria.Categoria;
import models.produto.Produto;
import models.produto.ProdutoDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

@WebServlet("/admin/estoque")
public class RelatorioProdutosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=relatorio-produtos.pdf");

        List<Produto> produtos;
        try {
            produtos = ProdutoDAO.obterTodos();
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao carregar produtos: " + e.getMessage());
            return;
        }

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDRectangle mediaBox = page.getMediaBox();
            float margin = 50;
            float yStart = mediaBox.getUpperRightY() - margin;
            float y = yStart;
            float leading = 16;

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            try {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, y);
                contentStream.showText("Relatório de Produtos");
                contentStream.endText();

                y -= leading * 2;

                // Cabeçalho
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, y);
                contentStream.showText("ID    Descrição                      Categoria                Preço     Qtde");
                contentStream.endText();

                y -= leading;
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                for (Produto p : produtos) {
                    if (y < margin + leading) {
                        contentStream.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        y = yStart;
                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                    }

                    String descricao = p.getDescricao() != null ? p.getDescricao() : "";
                    Categoria cat = p.getCategoria();
                    String categoria = (cat != null && cat.getNome() != null) ? cat.getNome() : "";
                    String preco = String.format("R$ %.2f", p.getPreco());
                    String quantidade = String.valueOf(p.getQuantidade());

                    String linha = String.format("%-5s %-30s %-22s %10s %5s",
                            String.valueOf(p.getId_produto()),
                            truncate(descricao, 30),
                            truncate(categoria, 22),
                            preco,
                            quantidade);

                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, y);
                    contentStream.showText(linha);
                    contentStream.endText();
                    y -= leading;
                }
            } finally {
                if (contentStream != null) {
                    contentStream.close();
                }
            }

            try (OutputStream out = resp.getOutputStream()) {
                document.save(out);
                out.flush();
            }
        }
    }

    private String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }
}

