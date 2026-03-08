package controllers.relatorio;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.venda.Venda;
import models.venda.VendaDAO;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

@WebServlet("/admin/relatorio-vendas")
public class RelatorioVendasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=relatorio-vendas.pdf");

        List<Venda> vendas;
        try {
            vendas = new VendaDAO().obterTodos();
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao carregar vendas: " + e.getMessage());
            return;
        }

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDRectangle mediaBox = page.getMediaBox();
            float margin = 50;
            float yStart = mediaBox.getUpperRightY() - margin;
            float y = yStart;
            float leading = 25;

            // Coordenadas ajustadas para: ID, Cliente, Data, Total, Status
            float[] cols = {50, 90, 240, 390, 475, 560};

            PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDType1Font fontNormal = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            try {
                // --- TÍTULO ---
                contentStream.setFont(fontBold, 18);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, y);
                contentStream.showText("Relatório Geral de Vendas");
                contentStream.endText();

                y -= 40;

                // --- CABEÇALHO ---
                contentStream.setFont(fontBold, 12);
                contentStream.setNonStrokingColor(44/255f, 62/255f, 80/255f);
                contentStream.addRect(cols[0], y - leading + 5, cols[5] - cols[0], leading);
                contentStream.fill();

                contentStream.setNonStrokingColor(1f, 1f, 1f);
                contentStream.beginText();
                contentStream.newLineAtOffset(cols[0] + 5, y - 12);
                contentStream.showText("ID");
                contentStream.newLineAtOffset(cols[1] - cols[0], 0); contentStream.showText("Cliente");
                contentStream.newLineAtOffset(cols[2] - cols[1], 0); contentStream.showText("Data");
                contentStream.newLineAtOffset(cols[3] - cols[2], 0); contentStream.showText("Total");
                contentStream.newLineAtOffset(cols[4] - cols[3], 0); contentStream.showText("Status");
                contentStream.endText();

                y -= leading;
                boolean zebrado = false;

                for (Venda v : vendas) {
                    if (y < margin + leading) {
                        contentStream.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        y = yStart;
                        contentStream = new PDPageContentStream(document, page);
                    }

                    // Fundo Zebrado
                    if (zebrado) {
                        contentStream.setNonStrokingColor(230/255f, 230/255f, 230/255f);
                    } else {
                        contentStream.setNonStrokingColor(248/255f, 248/255f, 248/255f);
                    }
                    contentStream.addRect(cols[0], y - leading + 5, cols[5] - cols[0], leading);
                    contentStream.fill();

                    // Dados da Venda
                    contentStream.setNonStrokingColor(0f, 0f, 0f);
                    contentStream.setFont(fontNormal, 10); // Fonte ligeiramente menor para dados longos

                    String cliente = truncate(v.getUsuario() != null ? v.getUsuario().getEmail() : "N/A", 25);
                    String data = v.getDataHora() != null ? sdf.format(v.getDataHora()) : "N/A";
                    String total = String.format("R$ %.2f", v.getValorTotal());
                    String status = v.getStatus();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(cols[0] + 5, y - 12);
                    contentStream.showText(String.valueOf(v.getIdVenda()));
                    contentStream.newLineAtOffset(cols[1] - cols[0], 0);
                    contentStream.showText(cliente);
                    contentStream.newLineAtOffset(cols[2] - cols[1], 0);
                    contentStream.showText(data);
                    contentStream.newLineAtOffset(cols[3] - cols[2], 0);
                    contentStream.showText(total);
                    contentStream.newLineAtOffset(cols[4] - cols[3], 0);
                    contentStream.showText(status);
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
            throw new ServletException(e);
        }
    }

    private String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }
}