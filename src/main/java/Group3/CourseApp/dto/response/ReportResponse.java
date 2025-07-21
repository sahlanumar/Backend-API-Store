package Group3.CourseApp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO utama yang mewakili keseluruhan data laporan.
 * Dibuat dengan gaya Lombok Builder agar konsisten dengan DTO lain seperti CustomerResponse.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportResponse {
    private String customerName;
    private String reportTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalAmount;
    private List<ReportItem> items; // Berisi detail rincian per produk atau pajak
}