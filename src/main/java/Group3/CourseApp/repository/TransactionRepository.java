package Group3.CourseApp.repository;

import Group3.CourseApp.dto.response.ReportItem; // Pastikan import DTO ini benar
import Group3.CourseApp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>, JpaSpecificationExecutor<Transaction> {

    // a. Total antara dua tanggal
    @Query("SELECT COALESCE(SUM(t.totalAmountPaid), 0.0) FROM Transaction t WHERE t.customer.id = :customerId AND t.transactionTime BETWEEN :start AND :end")
    double findTotalAmountByCustomerAndDateRange(@Param("customerId") String customerId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // b. Total sepanjang masa
    @Query("SELECT COALESCE(SUM(t.totalAmountPaid), 0.0) FROM Transaction t WHERE t.customer.id = :customerId")
    double findTotalAmountByCustomer(@Param("customerId") String customerId);

    // d. Total per produk
    @Query("SELECT new Group3.CourseApp.dto.response.ReportItem(p.name, SUM(td.quantity * td.price)) " +
            "FROM TransactionDetail td JOIN td.product p " +
            "WHERE td.transaction.customer.id = :customerId AND td.transaction.transactionTime BETWEEN :start AND :end " +
            "GROUP BY p.name ORDER BY SUM(td.quantity * td.price) DESC")
    List<ReportItem> findTotalAmountPerProduct(@Param("customerId") String customerId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // c. Total per pajak (sesuaikan dengan skema DB Anda)
    @Query("SELECT new Group3.CourseApp.dto.response.ReportItem(t.taxType, SUM(t.taxAmount)) " +
            "FROM Transaction t " +
            "WHERE t.customer.id = :customerId AND t.transactionTime BETWEEN :start AND :end " +
            "AND t.taxType IS NOT NULL " +
            "GROUP BY t.taxType ORDER BY SUM(t.taxAmount) DESC")
    List<ReportItem> findTotalAmountPerTax(@Param("customerId") String customerId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}