package Group3.CourseApp.dto.response;

import Group3.CourseApp.constant.PaymentMethod;
import Group3.CourseApp.constant.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllTransactionResponse {
    private String id;
    private String customerName;
    private Double totalAmountPaid;
    private LocalDateTime transactionTime;
    private TransactionStatus paymentStatus;

}
