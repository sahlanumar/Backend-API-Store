package Group3.CourseApp.dto.request;

import Group3.CourseApp.entity.Tax;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private Double price;
    private Set<String> taxesId;
}
