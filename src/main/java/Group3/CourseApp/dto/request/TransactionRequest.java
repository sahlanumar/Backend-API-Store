package Group3.CourseApp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private String customerId;
    private List<TransactionDetailRequest> details;
}
