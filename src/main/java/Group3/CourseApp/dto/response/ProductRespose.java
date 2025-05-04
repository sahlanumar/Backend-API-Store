package Group3.CourseApp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRespose {

    private String id;
    private String name;
    private Double price;
    private Integer taxRate;

}
