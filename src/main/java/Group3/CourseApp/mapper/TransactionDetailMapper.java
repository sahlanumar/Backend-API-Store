package Group3.CourseApp.mapper;

import Group3.CourseApp.dto.request.TransactionDetailRequest;
import Group3.CourseApp.dto.response.TransactionDetailResponse;
import Group3.CourseApp.entity.Product;
import Group3.CourseApp.entity.TransactionDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class TransactionDetailMapper {
    public static List<TransactionDetailResponse> toTransactionDetailList(List<TransactionDetail> transactionDetails) {
        List<TransactionDetailResponse> transactionDetailResponses = transactionDetails.stream()
                .map(transactionDetail -> TransactionDetailResponse.builder()
                        .productName(transactionDetail.getProduct().getName())
                        .quantity(transactionDetail.getQuantity())
                        .netPrice(transactionDetail.getNetPrice())
                        .taxAmount(transactionDetail.getTaxAmount())
                        .totalPrice(transactionDetail.getTotalPrice())
                        .build())
                .collect(toList());
        return transactionDetailResponses;
    }

}
