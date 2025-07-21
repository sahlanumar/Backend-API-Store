package Group3.CourseApp.Service;


import Group3.CourseApp.constant.ReportType;
import Group3.CourseApp.dto.response.ReportResponse;
import java.time.LocalDate;

/**
 * Interface for the reporting service.
 * Defines the contract for generating customer transaction reports.
 */
public interface ReportService {

    /**
     * Generates a transaction report for a specific customer based on the given type and date range.
     *
     * @param customerId The ID of the customer requesting the report.
     * @param type The type of report to generate (e.g., TOTAL_BETWEEN_DATES, BY_PRODUCT).
     * @param startDate The start date for the report period (optional).
     * @param endDate The end date for the report period (optional).
     * @return A ReportResponse DTO containing the structured report data.
     */
    ReportResponse generateReport(String customerId, ReportType type, LocalDate startDate, LocalDate endDate);

}