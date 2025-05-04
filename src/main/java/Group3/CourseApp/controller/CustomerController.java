package Group3.CourseApp.controller;

import Group3.CourseApp.Service.CustomerService;
import Group3.CourseApp.constant.ApiEndpoint;
import Group3.CourseApp.dto.request.CustomerRequest;
import Group3.CourseApp.dto.response.CommonResponse;
import Group3.CourseApp.dto.response.CustomerResponse;
import Group3.CourseApp.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById(@PathVariable String id) {
        CustomerResponse response = customerService.getCustomerById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Customer retrieved successfully", response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAllCustomers() {
        List<CustomerResponse> responseList = customerService.getAllCustomers();
        return ResponseUtil.buildResponse(HttpStatus.OK, "Customer list retrieved successfully", responseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomerById(
            @PathVariable String id,
            @Valid @RequestBody CustomerRequest customerRequest) {
        CustomerResponse response = customerService.updateCustomerById(id, customerRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Customer updated successfully", response);
    }
    @PutMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomerByLogin(
            @Valid @RequestBody CustomerRequest customerRequest) {
        CustomerResponse response = customerService.updateCustomerLogin( customerRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Customer updated successfully", response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteCustomerById(@PathVariable String id) {
        customerService.deleteCustomerById(id);
        return ResponseUtil.buildResponse(HttpStatus.NO_CONTENT, "Customer deleted successfully", null);
    }
}
