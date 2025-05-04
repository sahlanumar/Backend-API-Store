package Group3.CourseApp.Service.ServiceImpl;

import Group3.CourseApp.Service.CustomerService;
import Group3.CourseApp.Service.UserService;
import Group3.CourseApp.dto.request.CustomerRequest;
import Group3.CourseApp.dto.response.CustomerResponse;
import Group3.CourseApp.entity.Customer;
import Group3.CourseApp.entity.User;
import Group3.CourseApp.exception.CustomException;
import Group3.CourseApp.exception.ErrorCode;
import Group3.CourseApp.mapper.CustomerMapper;
import Group3.CourseApp.repository.CustomerRepository;
import Group3.CourseApp.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final JwtUtils jwtUtils;


    @Override
    public CustomerResponse getCustomerById(String id) {
        Customer customer = findCustomerById(id);
        return CustomerMapper.toCustomerResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return CustomerMapper.toCustomerResponseList(customers);
    }

    @Override
    public CustomerResponse updateCustomerById(String id, CustomerRequest customerRequest) {
        Customer customer = findCustomerById(id);
        if(customerRequest.getName() != null){
            customer.setName(customerRequest.getName());
        }
        if (customerRequest.getBirthDate() != null) {
            customer.setBirthDate(customerRequest.getBirthDate());
        }
        if (customerRequest.getBirthPlace() != null) {
            customer.setBirthPlace(customerRequest.getBirthPlace());
        }
        String token = jwtUtils.getTokenFromHeader();
        jwtUtils.validateJwtToken(token);
        String nameCreator = jwtUtils.getUsernameFromJwtToken(token);
        customer.setUpdatedBy(nameCreator);
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
        return CustomerMapper.toCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomerLogin(CustomerRequest customerRequest) {
        String token = jwtUtils.getTokenFromHeader();
        jwtUtils.validateJwtToken(token);
        String username = jwtUtils.getUsernameFromJwtToken(token);
        User user = userService.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Customer customer = findCustomerById(user.getUserId());
        if(customerRequest.getName() != null){
            customer.setName(customerRequest.getName());
        }
        if (customerRequest.getBirthDate() != null) {
            customer.setBirthDate(customerRequest.getBirthDate());
        }
        if (customerRequest.getBirthPlace() != null) {
            customer.setBirthPlace(customerRequest.getBirthPlace());
        }
        customer.setUpdatedBy(username);
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
        return CustomerMapper.toCustomerResponse(customer);
    }

    @Override
    public void deleteCustomerById(String id) {
        Customer customer = findCustomerById(id);
        User user = userService.findById(customer.getUserId());
        userService.delete(user.getUserId());
        customerRepository.delete(customer);
    }

    @Override
    public Customer findCustomerById(String id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer == null){
            User user = userService.findById(id);
            Customer customer1 = customerRepository.findByUserId(user.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));
            return customer1;
        }
        return customer;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}
