package com.majumundurcompany.onlineclothingmarketplace.service;

import com.majumundurcompany.onlineclothingmarketplace.dto.request.PagingRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.UpdateCustomerRequest;
import com.majumundurcompany.onlineclothingmarketplace.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    Customer create(Customer customer);
    Customer getById(String id);
    Page<Customer> getAll(PagingRequest request);
    Customer update(UpdateCustomerRequest request);
    Customer update(Customer customer);
    void deleteById(String id);
}
