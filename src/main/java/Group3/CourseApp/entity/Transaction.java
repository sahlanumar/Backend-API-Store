package Group3.CourseApp.entity;

import Group3.CourseApp.constant.PaymentMethod;
import Group3.CourseApp.constant.TableNames;
import Group3.CourseApp.constant.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = TableNames.TRANSACTION)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Builder.Default
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionDetail> details = new ArrayList<>();

    @Column(name = "net_amount_paid", nullable = false)
    private Double netAmountPaid;

    @Column(name = "total_tax_paid", nullable = false)
    private Double totalTaxPaid;

    @Column(name = "total_amount_paid", nullable = false)
    private Double totalAmountPaid;

    @Column(name = "transaction_time", nullable = false)
    private LocalDateTime transactionTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private TransactionStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "created_by", nullable = false)
    private String createdBy;
}
