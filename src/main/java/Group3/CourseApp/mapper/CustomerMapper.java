package Group3.CourseApp.mapper;

import Group3.CourseApp.dto.response.CustomerResponse;
import Group3.CourseApp.entity.Customer;

import java.util.List;

public class CustomerMapper {
    public static CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .userId(customer.getUserId())
                .name(customer.getName())
                .birthDate(customer.getBirthDate())
                .birthPlace(customer.getBirthPlace())
                .createdBy(customer.getCreatedBy())
                .createdAt(customer.getCreatedAt())
                .updatedBy(customer.getUpdatedBy())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
    public static List<CustomerResponse> toCustomerResponseList(List<Customer> customers) {
        List<CustomerResponse> customerResponseList = customers.stream().map(Customer -> toCustomerResponse(Customer) ).toList();
        return customerResponseList;
    }
}
