package Group3.CourseApp.Service;

public interface PdfGenerationService {
    public byte[] generateTransactionReportPdf(ReportResponse data);
}
