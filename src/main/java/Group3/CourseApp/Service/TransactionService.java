package Group3.CourseApp.Service;

import Group3.CourseApp.constant.PaymentMethod;
import Group3.CourseApp.constant.TransactionStatus;
import Group3.CourseApp.dto.request.TransactionRequest;
import Group3.CourseApp.dto.response.GetAllTransactionResponse;
import Group3.CourseApp.dto.response.TransactionReportResponse;
import Group3.CourseApp.dto.response.TransactionResponse;
import com.midtrans.httpclient.error.MidtransError;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest transactionRequest);
    TransactionResponse findById(String id);
    void deleteTransactionById(String id);
    TransactionResponse cancelTransaction(String id);
    TransactionResponse completeTransaction(String id, PaymentMethod paymentMethod);
    Page<GetAllTransactionResponse> getAllTransactionsDate(LocalDate startDate, LocalDate endDate, String customerName, List<TransactionStatus> paymentStatuses, List<PaymentMethod> paymentMethods, String createdBy, int page, int size, String sortField, String sortDirection);
    TransactionReportResponse getTotalAmountPaidByCustomerBetweenDates(String customerId, LocalDate startDate, LocalDate endDate);
    TransactionReportResponse financialReportCustomer( LocalDate startDate, LocalDate endDate);
    Map<String, String> createSnapToken(String transactionId) throws MidtransError;
    List<TransactionReportResponse> getTotalAmountPaidPerProduct();
    byte[] generateCustomerReportPdf(LocalDate startDate, LocalDate endDate);
    void saveFile(MultipartFile file)throws IOException;
    }
