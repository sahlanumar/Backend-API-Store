package Group3.CourseApp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailResponse {
    private String productName;
    private int quantity;
    private double netPrice;
    private double taxAmount;
    private double totalPrice;
}
