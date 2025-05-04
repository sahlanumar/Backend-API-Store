package Group3.CourseApp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxResponse {
    private String id;
    private String name;
    private Integer rate;
    private List<String> productIds;
}
