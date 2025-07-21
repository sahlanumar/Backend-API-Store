package Group3.CourseApp.Service;

import Group3.CourseApp.dto.response.ReportResponse;

public interface PdfGenerationService {
    public byte[] generateTransactionReportPdf(ReportResponse data);
}
