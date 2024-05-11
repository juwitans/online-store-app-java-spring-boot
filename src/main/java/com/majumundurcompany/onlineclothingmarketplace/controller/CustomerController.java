package com.majumundurcompany.onlineclothingmarketplace.controller;

import com.majumundurcompany.onlineclothingmarketplace.dto.request.PagingRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.UpdateCustomerRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.PagingResponse;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.WebResponse;
import com.majumundurcompany.onlineclothingmarketplace.entity.Customer;
import com.majumundurcompany.onlineclothingmarketplace.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getAllCustomers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PagingRequest request = PagingRequest.builder()
                .page(page)
                .size(size)
                .build();
        Page<Customer> customers = customerService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(request.getPage())
                .size(request.getSize())
                .build();

        WebResponse<List<Customer>> response = WebResponse.<List<Customer>>builder()
                .message("successfully get customers")
                .status(HttpStatus.OK.getReasonPhrase())
                .paging(pagingResponse)
                .data(customers.getContent())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        Customer customer = customerService.getById(id);
        WebResponse<Customer> response = WebResponse.<Customer>builder()
                .message("successfully get customer")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(customer)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        customerService.deleteById(id);
        WebResponse<Customer> response = WebResponse.<Customer>builder()
                .message("successfully delete customer")
                .status(HttpStatus.OK.getReasonPhrase())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody UpdateCustomerRequest customer) {
        Customer updated = customerService.update(customer);
        WebResponse<Customer> response = WebResponse.<Customer>builder()
                .message("successfully update customer")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(updated)
                .build();
        return ResponseEntity.ok(response);
    }
}
