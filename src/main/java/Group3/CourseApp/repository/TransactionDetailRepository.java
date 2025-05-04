package Group3.CourseApp.repository;

import Group3.CourseApp.dto.response.TransactionReportResponse;
import Group3.CourseApp.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String>, JpaSpecificationExecutor<TransactionDetail> {
    @Query("""
    SELECT new Group3.CourseApp.dto.response.TransactionReportResponse(td.product.name, SUM(td.totalPrice))
    FROM TransactionDetail td
    WHERE td.transaction.paymentStatus = Group3.CourseApp.constant.TransactionStatus.PAID
    GROUP BY td.product.name
""")
    List<TransactionReportResponse> getTotalAmountPaidPerProduct();
    //atau
   /* @Query(value = "SELECT p.name AS name, SUM(td.total_price) AS amount " +
            "FROM transaction_detail td " +
            "JOIN product p ON td.product_id = p.id " +
            "GROUP BY p.name",
            nativeQuery = true)
    List<TransactionReportResponse> getTotalAmountPaidPerProductNative();*/
}
