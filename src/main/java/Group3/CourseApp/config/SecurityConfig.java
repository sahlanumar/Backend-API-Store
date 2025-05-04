package Group3.CourseApp.config;



import Group3.CourseApp.security.AuthEntryPointJwt;
import Group3.CourseApp.security.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final AuthTokenFilter authTokenFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        menyediakan satpam mengecek token
//         JWT kan DISINI DOANG
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register-admin").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register-staff").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/auth/register-customer").hasAnyRole("ADMIN","STAFF")

                        //user
                        .requestMatchers(HttpMethod.GET,"/api/users/{id}").permitAll()//karena jika mengetahui id berarti bisa mencari data sendiri(id diri sendiri harus dijaga)
                        .requestMatchers(HttpMethod.GET,"/api/users").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.PUT,"/api/users/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/users").permitAll()//karena update profil sendiri
                        .requestMatchers(HttpMethod.DELETE,"/api/users/{id}").hasAnyRole("ADMIN", "STAFF")

                        //customer
                        .requestMatchers(HttpMethod.GET,"/api/customers/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/customers").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.PUT,"/api/customers/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/customers").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/customers/{id}").hasAnyRole("ADMIN", "STAFF")

                        //tax
                        .requestMatchers(HttpMethod.GET,"/api/taxes/{id}").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.GET,"/api/taxes").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.PUT,"/api/taxes/{id}").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.POST,"/api/taxes").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.DELETE,"/api/taxes/{id}").hasAnyRole("ADMIN", "STAFF")

                        //product
                        .requestMatchers(HttpMethod.GET,"/api/products/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/products").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/products/{id}").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.POST,"/api/products").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.DELETE,"/api/products/{id}").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.POST,"/api/products/{productId}/tax/{taxId}").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.DELETE,"/api/products/{productId}/tax/{taxId}").hasAnyRole("ADMIN", "STAFF")

                        //transaction
                        .requestMatchers(HttpMethod.GET,"/api/transactions/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/transactions").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.PUT,"/api/transactions/{id}/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.POST,"/api/transactions").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.DELETE,"/api/transactions/{id}").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.GET,"/api/transactions/report/pdf").hasAnyRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/transactions/report/total-paid-per-product").hasAnyRole("ADMIN", "STAFF")




                        .anyRequest().authenticated()
                );


        http.authenticationProvider(authenticationProvider());

        return http.build();
    }
}
