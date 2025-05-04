package Group3.CourseApp.Service;

import Group3.CourseApp.dto.request.CustomerRequest;
import Group3.CourseApp.dto.response.CustomerResponse;
import Group3.CourseApp.entity.Customer;

import java.util.List;

public interface CustomerService {
    CustomerResponse getCustomerById(String id);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse updateCustomerById(String id, CustomerRequest customerRequest);
    CustomerResponse updateCustomerLogin( CustomerRequest customerRequest);
    void deleteCustomerById(String id);
    Customer findCustomerById(String id);
    Customer save(Customer customer);

}
