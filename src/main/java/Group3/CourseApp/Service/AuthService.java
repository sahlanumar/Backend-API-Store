package Group3.CourseApp.Service;

import Group3.CourseApp.dto.request.LoginRequest;
import Group3.CourseApp.dto.request.RegisterCustomerRequest;
import Group3.CourseApp.dto.request.RegisterRequest;
import Group3.CourseApp.dto.response.JwtResponse;
import Group3.CourseApp.dto.response.UserResponse;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);
    UserResponse registerStaff(RegisterRequest registerRequest);
    UserResponse registerCustomer(RegisterCustomerRequest registerCustomerRequest);
    UserResponse registerAdmin(RegisterRequest registerRequest,String secretKey);

}
