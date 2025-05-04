package Group3.CourseApp.Service.ServiceImpl;

import Group3.CourseApp.Service.AuthService;
import Group3.CourseApp.Service.CustomerService;
import Group3.CourseApp.Service.RoleService;
import Group3.CourseApp.Service.UserService;
import Group3.CourseApp.constant.UserRole;
import Group3.CourseApp.dto.request.LoginRequest;
import Group3.CourseApp.dto.request.RegisterCustomerRequest;
import Group3.CourseApp.dto.request.RegisterRequest;
import Group3.CourseApp.dto.response.JwtClaims;
import Group3.CourseApp.dto.response.JwtResponse;
import Group3.CourseApp.dto.response.UserResponse;
import Group3.CourseApp.entity.Customer;
import Group3.CourseApp.entity.Role;
import Group3.CourseApp.entity.User;
import Group3.CourseApp.security.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    @Value("${course.super-admin.secret-key}")
    private String courseSecretKey;

    public JwtResponse login(LoginRequest loginRequest) {
        if(loginRequest.getUsername() == null && loginRequest.getPassword() == null) {
            throw new RuntimeException("Username dan password tidak boleh kosong");
        }
        Authentication authentication = null;
        if(loginRequest.getUsername() == null) {
             authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        }else{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        }

        String jwt = jwtUtils.generateToken((UserDetails) authentication.getPrincipal());

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        return JwtResponse.builder().
                token(jwt).username(userDetails.getUsername())
                .roles(roles)
                .build();
    }

    @Override
    @Transactional
    public UserResponse registerStaff(RegisterRequest registerRequest) {
        if(userService.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email sudah terdaftar");
        }

        if(userService.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username sudah terdaftar");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();

        Role userRole = roleService.getOrCreate(UserRole.ROLE_STAFF);
        roles.add(userRole);

        user.setRoles(roles);

        userService.save(user);

        UserResponse userResponse = UserResponse.builder()
                .name(user.getUsername())
                .id(user.getUserId())
                .email(user.getEmail())
                .role("ROLE_STAFF")
                .build();
        return userResponse;
    }

    @Override
    @Transactional
    public UserResponse registerCustomer(RegisterCustomerRequest registerCustomerRequest) {
        if(userService.existsByEmail(registerCustomerRequest.getEmail())) {
            throw new RuntimeException("Email sudah terdaftar");
        }

        if(userService.existsByUsername(registerCustomerRequest.getUsername())) {
            throw new RuntimeException("Username sudah terdaftar");
        }

        User user = User.builder()
                .username(registerCustomerRequest.getUsername())
                .email(registerCustomerRequest.getEmail())
                .password(passwordEncoder.encode(registerCustomerRequest.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();

        Role userRole = roleService.getOrCreate(UserRole.ROLE_CUSTOMER);
        roles.add(userRole);

        user.setRoles(roles);

        userService.save(user);

        String token = jwtUtils.getTokenFromHeader();
        jwtUtils.validateJwtToken(token);
        String nameCreator = jwtUtils.getUsernameFromJwtToken(token);

        Customer customer = Customer.builder()
                .name(registerCustomerRequest.getName())
                .birthDate(registerCustomerRequest.getBirthDate())
                .birthPlace(registerCustomerRequest.getBirthPlace())
                .createdBy(nameCreator)
                .createdAt(LocalDateTime.now())
                .userId(user.getUserId())
                .build();

        customerService.save(customer);

        UserResponse userResponse = UserResponse.builder()
                .name(user.getUsername())
                .id(user.getUserId())
                .email(user.getEmail())
                .role(user.getRoles().stream().findFirst().get().getName().name())
                .build();
        return userResponse;
    }

    public UserResponse registerAdmin(RegisterRequest registerRequest,String key) {
        log.info("Received superAdminKey: {}", key);
        log.info("Expected superAdminKey: {}", courseSecretKey);
        if(key.equals(courseSecretKey)){
            if(userService.existsByEmail(registerRequest.getEmail())) {
                throw new RuntimeException("Email sudah terdaftar");
            }

            if(userService.existsByUsername(registerRequest.getUsername())) {
                throw new RuntimeException("Username sudah terdaftar");
            }

            User user = User.builder()
                    .username(registerRequest.getUsername())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .build();

            Set<Role> roles = new HashSet<>();

            Role userRole = roleService.getOrCreate(UserRole.ROLE_ADMIN);
            roles.add(userRole);

            user.setRoles(roles);

            userService.save(user);

            UserResponse userResponse = UserResponse.builder()
                    .name(user.getUsername())
                    .id(user.getUserId())
                    .email(user.getEmail())
                    .role("ADMIN")
                    .build();
            return userResponse;
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid super admin key");
        }
    }





}
