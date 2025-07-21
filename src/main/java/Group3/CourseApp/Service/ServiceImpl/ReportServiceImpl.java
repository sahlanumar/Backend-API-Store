package Group3.CourseApp.Service.ServiceImpl;


import Group3.CourseApp.Service.ReportService;
import Group3.CourseApp.constant.ReportType;
import Group3.CourseApp.dto.response.ReportItem;
import Group3.CourseApp.dto.response.ReportResponse;

import Group3.CourseApp.entity.Customer;
import Group3.CourseApp.repository.CustomerRepository;
import Group3.CourseApp.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the ReportService interface.
 * This class contains the business logic for creating report data.
 */
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;

    @Override
    public ReportResponse generateReport(String customerId, ReportType type, LocalDate startDate, LocalDate endDate) {
        // Set default date range if not provided by the user
        LocalDate finalStartDate = (startDate == null) ? LocalDate.now().minusYears(10) : startDate;
        LocalDate finalEndDate = (endDate == null) ? LocalDate.now() : endDate;
        LocalDateTime startDateTime = finalStartDate.atStartOfDay();
        LocalDateTime endDateTime = finalEndDate.atTime(23, 59, 59);

        // Get customer name from repository
        String customerName = customerRepository.findById(customerId)
                .map(Customer::getName) // Assumes Customer entity has a getName() method
                .orElse("Unknown Customer");

        // Use a switch to delegate to the appropriate private method
        return switch (type) {
            case TOTAL_BETWEEN_DATES -> generateTotalBetweenDatesReport(customerId, customerName, finalStartDate, finalEndDate, startDateTime, endDateTime);
            case LIFETIME_TOTAL -> generateLifetimeTotalReport(customerId, customerName);
            case BY_PRODUCT -> generateReportByProduct(customerId, customerName, finalStartDate, finalEndDate, startDateTime, endDateTime);
            case BY_TAX -> generateReportByTax(customerId, customerName, finalStartDate, finalEndDate, startDateTime, endDateTime);
        };
    }

    private ReportResponse generateTotalBetweenDatesReport(String customerId, String customerName, LocalDate startDate, LocalDate endDate, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        double total = transactionRepository.findTotalAmountByCustomerAndDateRange(customerId, startDateTime, endDateTime);
        return ReportResponse.builder()
                .customerName(customerName)
                .reportTitle("Total Spending Report")
                .startDate(startDate)
                .endDate(endDate)
                .totalAmount(total)
                .items(Collections.emptyList())
                .build();
    }

    private ReportResponse generateLifetimeTotalReport(String customerId, String customerName) {
        double total = transactionRepository.findTotalAmountByCustomer(customerId);
        return ReportResponse.builder()
                .customerName(customerName)
                .reportTitle("Lifetime Spending Report")
                .totalAmount(total)
                .build();
    }

    private ReportResponse generateReportByProduct(String customerId, String customerName, LocalDate startDate, LocalDate endDate, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<ReportItem> items = transactionRepository.findTotalAmountPerProduct(customerId, startDateTime, endDateTime);
        double total = items.stream().mapToDouble(ReportItem::getAmount).sum();
        return ReportResponse.builder()
                .customerName(customerName)
                .reportTitle("Spending by Product Report")
                .startDate(startDate)
                .endDate(endDate)
                .totalAmount(total)
                .items(items)
                .build();
    }

    private ReportResponse generateReportByTax(String customerId, String customerName, LocalDate startDate, LocalDate endDate, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<ReportItem> items = transactionRepository.findTotalAmountPerTax(customerId, startDateTime, endDateTime);
        double total = items.stream().mapToDouble(ReportItem::getAmount).sum();
        return ReportResponse.builder()
                .customerName(customerName)
                .reportTitle("Spending by Tax Report")
                .startDate(startDate)
                .endDate(endDate)
                .totalAmount(total)
                .items(items)
                .build();
    }
}