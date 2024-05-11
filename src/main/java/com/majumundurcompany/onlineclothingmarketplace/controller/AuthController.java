package com.majumundurcompany.onlineclothingmarketplace.controller;

import com.majumundurcompany.onlineclothingmarketplace.dto.request.AdminRegisterRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.CustomerRegisterRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.LoginRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.AdminRegisterResponse;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.CustomerRegisterResponse;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.LoginResponse;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.WebResponse;
import com.majumundurcompany.onlineclothingmarketplace.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasAnyRole;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody CustomerRegisterRequest request) {
        CustomerRegisterResponse customerRegisterResponse = authService.registerCustomer(request);
        WebResponse<CustomerRegisterResponse> response = WebResponse.<CustomerRegisterResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("successfully create new user")
                .data(customerRegisterResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/register/admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRegisterRequest request) {
        AdminRegisterResponse adminRegisterResponse = authService.registerAdmin(request);
        WebResponse<AdminRegisterResponse> response = WebResponse.<AdminRegisterResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("successfully create new admin")
                .data(adminRegisterResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LoginResponse login = authService.login(request);
        WebResponse<LoginResponse> response = WebResponse.<LoginResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("successfully login")
                .data(login)
                .build();
        return ResponseEntity.ok(response);
    }
}
