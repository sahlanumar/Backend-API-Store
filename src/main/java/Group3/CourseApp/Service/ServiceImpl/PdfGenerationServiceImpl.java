package Group3.CourseApp.Service.ServiceImpl;

import Group3.CourseApp.Service.PdfGenerationService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PdfGenerationServiceImpl implements PdfGenerationService {
    public byte[] generateTransactionReportPdf(ReportResponse data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
             document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // --- Header ---
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph(data.reportTitle(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // --- Informasi Laporan ---
            document.add(new Paragraph("Customer: " + data.customerName()));
            if (data.startDate() != null && data.endDate() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
                document.add(new Paragraph("Period: " + data.startDate().format(formatter) + " - " + data.endDate().format(formatter)));
            }
            document.add(Chunk.NEWLINE);

            // --- Tabel Detail ---
            if (data.items() != null && !data.items().isEmpty()) {
                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{3, 2});

                // Header Tabel
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                table.addCell(new Phrase("Description", headerFont));
                table.addCell(new Phrase("Amount (Rp)", headerFont));

                for (ReportItem item : data.items()) {
                    table.addCell(item.label());
                    table.addCell(String.format("%,.2f", item.amount()));
                }
                document.add(table);
            }

            // --- Total ---
            Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Paragraph total = new Paragraph(String.format("Total Amount: Rp %,.2f", data.totalAmount()), totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to generate PDF report", e);
        }
        return out.toByteArray();
    }
}
