package Group3.CourseApp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCustomerRequest {

    @NotBlank(message = "Username tidak boleh kosong")
    private String username;

    @NotBlank(message = "Email tidak boleh kosong")
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    private String password;

    @NotBlank(message = "name tidak boleh kosong")
    private String name;

    @NotBlank(message = "birthPlace tidak boleh kosong")
    private String birthPlace;

    @NotBlank(message = "birthDate tidak boleh kosong")
    private String birthDate;

}
