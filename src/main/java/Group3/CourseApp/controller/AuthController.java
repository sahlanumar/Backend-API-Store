package Group3.CourseApp.controller;


import Group3.CourseApp.Service.AuthService;
import Group3.CourseApp.dto.request.LoginRequest;
import Group3.CourseApp.dto.request.RegisterCustomerRequest;
import Group3.CourseApp.dto.request.RegisterRequest;
import Group3.CourseApp.dto.response.CommonResponse;
import Group3.CourseApp.dto.response.JwtResponse;
import Group3.CourseApp.dto.response.UserResponse;
import Group3.CourseApp.entity.User;
import Group3.CourseApp.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<JwtResponse>> login(
            @Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);

        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                "Login berhasil",
                jwtResponse
        );
    }

//    register customer
    @PostMapping("/register-staff")
    public ResponseEntity<CommonResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest signupRequest) {
        UserResponse userResponse = authService.registerStaff(signupRequest);

        return ResponseUtil.buildResponse(
                HttpStatus.CREATED,
                "Registrasi berhasil",
                userResponse
        );
    }

//    register super admin
    @PostMapping("/register-admin")
    public ResponseEntity<CommonResponse<UserResponse>> registerSuperAdmin(
            @Valid @RequestBody RegisterRequest signupRequest, @RequestHeader("superAdminKey") String superAdminKey) {
        UserResponse userResponse = authService.registerAdmin(signupRequest, superAdminKey);

        return ResponseUtil.buildResponse(
                HttpStatus.CREATED,
                "Registrasi Admin berhasil",
                userResponse
        );
    }

    @PostMapping("/register-customer")
    public ResponseEntity<CommonResponse<UserResponse>> registerCustomer(
            @Valid @RequestBody RegisterCustomerRequest registerCustomerRequest) {
        UserResponse userResponse = authService.registerCustomer(registerCustomerRequest);
        return ResponseUtil.buildResponse(
                HttpStatus.CREATED,
                "Registrasi Customer berhasil",
                userResponse
        );
    }
}
