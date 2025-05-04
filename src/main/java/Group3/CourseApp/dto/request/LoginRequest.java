package Group3.CourseApp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    private String username;

    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    private String password;

}
