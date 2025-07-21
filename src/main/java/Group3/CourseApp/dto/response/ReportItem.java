package Group3.CourseApp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO untuk menampung satu baris item dalam laporan,
 * contoh: "Nama Produk", 150000.0.
 * Dibuat dengan gaya Lombok Builder.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportItem {
    private String label;
    private double amount;
}