package com.majumundurcompany.onlineclothingmarketplace.service;

import com.majumundurcompany.onlineclothingmarketplace.dto.request.AdminRegisterRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.CustomerRegisterRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.LoginRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.AdminRegisterResponse;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.CustomerRegisterResponse;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.LoginResponse;

public interface AuthService {
    AdminRegisterResponse registerAdmin(AdminRegisterRequest request);
    CustomerRegisterResponse registerCustomer(CustomerRegisterRequest request);
    LoginResponse login(LoginRequest request);
}
