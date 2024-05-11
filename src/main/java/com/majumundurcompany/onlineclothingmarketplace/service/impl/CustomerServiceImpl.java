package com.majumundurcompany.onlineclothingmarketplace.service.impl;

import com.majumundurcompany.onlineclothingmarketplace.dto.request.PagingRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.UpdateCustomerRequest;
import com.majumundurcompany.onlineclothingmarketplace.entity.Customer;
import com.majumundurcompany.onlineclothingmarketplace.repository.CustomerRepository;
import com.majumundurcompany.onlineclothingmarketplace.service.CustomerService;
import com.majumundurcompany.onlineclothingmarketplace.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<Customer> getAll(PagingRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        return customerRepository.findAll(pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer update(UpdateCustomerRequest request) {
        validationUtil.validate(request);
        Customer customer = findByIdOrThrowNotFound(request.getId());
        customer.setAddress(request.getAddress());
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer update(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        Customer customer = findByIdOrThrowNotFound(id);
        customerRepository.delete(customer);
    }

    private Customer findByIdOrThrowNotFound(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
    }
}
