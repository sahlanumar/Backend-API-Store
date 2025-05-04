package Group3.CourseApp.mapper;

import Group3.CourseApp.dto.response.GetAllTransactionResponse;
import Group3.CourseApp.dto.response.TransactionResponse;
import Group3.CourseApp.entity.Transaction;

public class TransactionMapper {
    public static TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .customerName(transaction.getCustomer().getName())
                .details(TransactionDetailMapper.toTransactionDetailList(transaction.getDetails()))
                .netAmountPaid(transaction.getNetAmountPaid())
                .totalTaxPaid(transaction.getTotalTaxPaid())
                .totalAmountPaid(transaction.getTotalAmountPaid())
                .transactionTime(transaction.getTransactionTime())
                .paymentStatus(transaction.getPaymentStatus())
                .paymentMethod(transaction.getPaymentMethod())
                .createdBy(transaction.getCreatedBy())
                .build();
    }
    public static GetAllTransactionResponse toGetAllTransactionResponse(Transaction transaction) {
        return GetAllTransactionResponse.builder()
                .id(transaction.getId())
                .customerName(transaction.getCustomer().getName())
                .totalAmountPaid(transaction.getTotalAmountPaid())
                .transactionTime(transaction.getTransactionTime())
                .paymentStatus(transaction.getPaymentStatus())
                .build();
    }
}
