package Group3.CourseApp.entity;

import Group3.CourseApp.constant.TableNames;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = TableNames.TRANSACTION_DETAIL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "net_price", nullable = false)
    private Double netPrice;

    @Column(name = "tax_amount", nullable = false)
    private Double taxAmount;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;
}
