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
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

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

            // --- CONFIGURAÇÕES VISUAIS ---
            float leading = 25;
            float[] cols = {50, 100, 310, 440, 510, 560};
            PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDType1Font fontNormal = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            try {
                // --- TÍTULO (AGORA PADRONIZADO) ---
                // Mudei de Courier para Helvetica Bold (Tamanho 18)
                contentStream.setFont(fontBold, 18);
                contentStream.setNonStrokingColor(0f, 0f, 0f); // Garante preto
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, y);
                contentStream.showText("Relatório de Produtos");
                contentStream.endText();

                y -= 40; // Espaço maior depois do título para respirar

                // --- CABEÇALHO ---
                contentStream.setFont(fontBold, 12);

                // 1. Fundo Azul Escuro
                contentStream.setNonStrokingColor(44/255f, 62/255f, 80/255f);
                contentStream.addRect(cols[0], y - leading + 5, cols[5] - cols[0], leading);
                contentStream.fill();

                // 2. Texto/Linhas Brancas
                contentStream.setStrokingColor(1f, 1f, 1f);
                contentStream.setNonStrokingColor(1f, 1f, 1f);

                // 3. Bordas
                contentStream.addRect(cols[0], y - leading + 5, cols[5] - cols[0], leading);
                contentStream.stroke();
                for (float x : cols) {
                    contentStream.moveTo(x, y + 5);
                    contentStream.lineTo(x, y - leading + 5);
                    contentStream.stroke();
                }

                // 4. Texto
                contentStream.beginText();
                contentStream.newLineAtOffset(cols[0] + 5, y - 12);
                contentStream.showText("ID");
                contentStream.newLineAtOffset(cols[1] - cols[0], 0); contentStream.showText("Descrição");
                contentStream.newLineAtOffset(cols[2] - cols[1], 0); contentStream.showText("Categoria");
                contentStream.newLineAtOffset(cols[3] - cols[2], 0); contentStream.showText("Preço");
                contentStream.newLineAtOffset(cols[4] - cols[3], 0); contentStream.showText("Qtde");
                contentStream.endText();

                y -= leading;
                contentStream.setFont(fontNormal, 12);

                boolean zebrado = false;

                for (Produto p : produtos) {
                    if (y < margin + leading) {
                        contentStream.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        y = yStart;
                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(fontNormal, 12);
                    }

                    if (zebrado) {
                        contentStream.setNonStrokingColor(230/255f, 230/255f, 230/255f);
                    } else {
                        contentStream.setNonStrokingColor(248/255f, 248/255f, 248/255f);
                    }
                    contentStream.addRect(cols[0], y - leading + 5, cols[5] - cols[0], leading);
                    contentStream.fill();

                    contentStream.setNonStrokingColor(0f, 0f, 0f);
                    contentStream.setStrokingColor(1f, 1f, 1f);

                    contentStream.addRect(cols[0], y - leading + 5, cols[5] - cols[0], leading);
                    contentStream.stroke();
                    for (float x : cols) {
                        contentStream.moveTo(x, y + 5);
                        contentStream.lineTo(x, y - leading + 5);
                        contentStream.stroke();
                    }

                    String descricao = truncate(p.getDescricao(), 30);
                    String categoria = truncate(p.getCategoria() != null ? p.getCategoria().getNome() : "", 22);
                    String preco = String.format("R$ %.2f", p.getPreco());
                    String quantidade = String.valueOf(p.getQuantidade());
                    String id = String.valueOf(p.getId_produto());

                    contentStream.beginText();
                    contentStream.newLineAtOffset(cols[0] + 5, y - 12);
                    contentStream.showText(id);
                    contentStream.newLineAtOffset(cols[1] - cols[0], 0);
                    contentStream.showText(descricao);
                    contentStream.newLineAtOffset(cols[2] - cols[1], 0);
                    contentStream.showText(categoria);
                    contentStream.newLineAtOffset(cols[3] - cols[2], 0);
                    contentStream.showText(preco);
                    contentStream.newLineAtOffset(cols[4] - cols[3], 0);
                    contentStream.showText(quantidade);
                    contentStream.endText();

                    y -= leading;
                    zebrado = !zebrado;
                }
            } finally {
                contentStream.close();
            }

            try (OutputStream out = resp.getOutputStream()) {
                document.save(out);
                out.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }
}