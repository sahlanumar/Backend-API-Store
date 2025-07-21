package Group3.CourseApp.controller;


import Group3.CourseApp.Service.PdfGenerationService;
import Group3.CourseApp.Service.ReportService;
import Group3.CourseApp.Service.TransactionService;
import Group3.CourseApp.constant.*;
import Group3.CourseApp.dto.request.TransactionRequest;
import Group3.CourseApp.dto.response.*;
import Group3.CourseApp.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ApiEndpoint.TRANSACTION)
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final ReportService reportService;
    private final PdfGenerationService pdfGenerationService;

    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(@RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.createTransaction(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Transaction created successfully", response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TransactionResponse>> getTransactionById(@PathVariable String id) {
        TransactionResponse response = transactionService.findById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Transaction retrieved successfully", response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteTransaction(@PathVariable String id) {
        transactionService.deleteTransactionById(id);
        return ResponseUtil.buildResponse(HttpStatus.NO_CONTENT, "Transaction deleted successfully", null);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<CommonResponse<TransactionResponse>> cancelTransaction(@PathVariable String id) {
        TransactionResponse response = transactionService.cancelTransaction(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Transaction cancelled successfully", response);
    }

    @PostMapping("/pay/{transactionId}")
    public ResponseEntity<Map<String, String>> payTransaction(@PathVariable String transactionId) {
        try {
            Map<String, String> response = transactionService.createSnapToken(transactionId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create Snap token: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<GetAllTransactionResponse>>> getAllTransactions(
            @RequestParam(defaultValue = "2000-01-01") LocalDate startDate,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().toString()}") LocalDate endDate,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) List<TransactionStatus> paymentStatuses,
            @RequestParam(required = false) List<PaymentMethod> paymentMethods,
            @RequestParam(required = false) String createdBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "transactionTime") String sortField,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Page<GetAllTransactionResponse> response = transactionService.getAllTransactionsDate(
                startDate, endDate, customerName, paymentStatuses, paymentMethods, createdBy, page, size, sortField, sortDirection
        );
        return ResponseUtil.buildResponse(HttpStatus.OK, "Transactions retrieved successfully", response.getContent(), response);
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            transactionService.saveFile(file);
            return ResponseEntity.ok("File berhasil diunggah: " + file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal mengunggah file");
        }
    }


    @GetMapping("/me/reports") // Konvensi: '/me' untuk data milik user yang sedang login
    public ResponseEntity<?> generateMyReport(
            // 🔒 Ambil user yang sedang login dari konteks keamanan, BUKAN dari @RequestParam
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam ReportType type,
            @RequestParam(defaultValue = "JSON") ReportFormat format,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        // Asumsi username adalah ID customer, sesuaikan jika perlu
        String customerId = userDetails.getUsername();

        // 1. Generate data laporan terlebih dahulu
        ReportResponse reportData = reportService.generateReport(customerId, type, startDate, endDate);

        // 2. Tentukan format output (PDF atau JSON)
        if (format == ReportFormat.PDF) {
            // Jika format PDF, panggil service untuk membuat file PDF
            byte[] pdfBytes = pdfGenerationService.generateTransactionReportPdf(reportData);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "my-transaction-report.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } else {
            // Format default adalah JSON
            return ResponseUtil.buildResponse(HttpStatus.OK, "Report generated successfully", reportData);
        }
    }
}

