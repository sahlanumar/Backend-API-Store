package Group3.CourseApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    // Tax-excluded price
    @Column(nullable = false)
    private Double price;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "public_id")
    private String publicId;

    @ManyToMany
    @JoinTable(
            name = "product_tax",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tax_id")
    )
    private Set<Tax> taxes;
}

