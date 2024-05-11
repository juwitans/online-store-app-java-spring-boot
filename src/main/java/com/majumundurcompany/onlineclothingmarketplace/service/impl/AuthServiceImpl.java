package com.majumundurcompany.onlineclothingmarketplace.service.impl;

import com.majumundurcompany.onlineclothingmarketplace.constant.ERole;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.AdminRegisterRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.CustomerRegisterRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.LoginRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.AdminRegisterResponse;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.CustomerRegisterResponse;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.LoginResponse;
import com.majumundurcompany.onlineclothingmarketplace.entity.Customer;
import com.majumundurcompany.onlineclothingmarketplace.entity.Role;
import com.majumundurcompany.onlineclothingmarketplace.entity.User;
import com.majumundurcompany.onlineclothingmarketplace.repository.UserRepository;
import com.majumundurcompany.onlineclothingmarketplace.security.JwtUtils;
import com.majumundurcompany.onlineclothingmarketplace.service.AuthService;
import com.majumundurcompany.onlineclothingmarketplace.service.CustomerService;
import com.majumundurcompany.onlineclothingmarketplace.service.RoleService;
import com.majumundurcompany.onlineclothingmarketplace.util.ValidationUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final JwtUtils jwtUtils;
    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;

    @PostConstruct
    @Transactional(rollbackFor = Exception.class)
    public void initAdmin() {
        Optional<User> optionalUser = userRepository.findByUsername("admin123");

        if (optionalUser.isPresent()) return;

        Role roleAdmin = roleService.getOrSave(ERole.ROLE_ADMIN);

        String hashPassword = passwordEncoder.encode("password123");

        User user = User.builder()
                .email("superadmin@gmail.com")
                .username("admin123")
                .name("Admin 123")
                .phoneNumber("08123459")
                .password(hashPassword)
                .roles(List.of(roleAdmin))
                .build();
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdminRegisterResponse registerAdmin(AdminRegisterRequest request) {
        try {
            validationUtil.validate(request);
            Role role = roleService.getOrSave(ERole.ROLE_ADMIN);

            User user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .name(request.getName())
                    .phoneNumber(request.getPhoneNumber())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userRepository.saveAndFlush(user);

            return AdminRegisterResponse.builder()
                    .username(user.getUsername())
                    .role(role.getRole().name())
                    .createdDate(new Date())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account already exist, please login to continue");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerRegisterResponse registerCustomer(CustomerRegisterRequest request) {
        try {
            validationUtil.validate(request);
            Role role = roleService.getOrSave(ERole.ROLE_CUSTOMER);

            User user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .name(request.getName())
                    .phoneNumber(request.getPhoneNumber())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userRepository.saveAndFlush(user);

            Customer customer = Customer.builder()
                    .id(user.getId())
                    .address(request.getAddress())
                    .point(0)
                    .build();
            customerService.create(customer);

            return CustomerRegisterResponse.builder()
                    .username(user.getUsername())
                    .role(role.getRole().name())
                    .createdDate(new Date())
                    .build();

        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account already exist, please login to continue");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResponse login(LoginRequest request) {
        validationUtil.validate(request);
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        User user = (User) authenticate.getPrincipal();
        String token = jwtUtils.generateToken(user);
        return LoginResponse.builder()
                .username(user.getUsername())
                .token(token)
                .build();
    }
}
