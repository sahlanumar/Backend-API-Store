package Group3.CourseApp.Service.ServiceImpl;

import Group3.CourseApp.Service.PdfGenerationService;
import Group3.CourseApp.dto.response.ReportItem;
import Group3.CourseApp.dto.response.ReportResponse;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PdfGenerationServiceImpl implements PdfGenerationService {

    @Override
    public byte[] generateTransactionReportPdf(ReportResponse data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // --- Header ---
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph(data.getReportTitle(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // --- Informasi Laporan ---
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Customer: " + data.getCustomerName(), bodyFont));
            if (data.getStartDate() != null && data.getEndDate() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
                String period = "Period: " + data.getStartDate().format(formatter) + " - " + data.getEndDate().format(formatter);
                document.add(new Paragraph(period, bodyFont));
            }
            document.add(Chunk.NEWLINE);

            // --- Tabel Detail ---
            if (data.getItems() != null && !data.getItems().isEmpty()) {
                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{3f, 2f});

                // Header Tabel
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
                PdfPCell headerCell1 = new PdfPCell(new Phrase("Description", headerFont));
                PdfPCell headerCell2 = new PdfPCell(new Phrase("Amount (Rp)", headerFont));
                headerCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(headerCell1);
                table.addCell(headerCell2);

                // Isi Tabel
                for (ReportItem item : data.getItems()) {
                    table.addCell(new Phrase(item.getLabel(), bodyFont));
                    PdfPCell amountCell = new PdfPCell(new Phrase(String.format("%,.2f", item.getAmount()), bodyFont));
                    amountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.addCell(amountCell);
                }
                document.add(table);
            }
            document.add(Chunk.NEWLINE);

            // --- Total ---
            Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph total = new Paragraph(String.format("Total Amount: Rp %,.2f", data.getTotalAmount()), totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to generate PDF report", e);
        }
        return out.toByteArray();
    }
}