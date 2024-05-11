package com.majumundurcompany.onlineclothingmarketplace.service.impl;

import com.majumundurcompany.onlineclothingmarketplace.constant.ERole;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.MerchantRegisterRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.PagingRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.UpdateMerchantRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.MerchantRegisterResponse;
import com.majumundurcompany.onlineclothingmarketplace.entity.Merchant;
import com.majumundurcompany.onlineclothingmarketplace.entity.Role;
import com.majumundurcompany.onlineclothingmarketplace.entity.User;
import com.majumundurcompany.onlineclothingmarketplace.repository.MerchantRepository;
import com.majumundurcompany.onlineclothingmarketplace.service.MerchantService;
import com.majumundurcompany.onlineclothingmarketplace.service.RoleService;
import com.majumundurcompany.onlineclothingmarketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;
    private final UserService userService;
    private final RoleService roleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MerchantRegisterResponse create(MerchantRegisterRequest request) {
        User user = userService.loadUserById(request.getUserId());
        Role roleMerchant = roleService.getOrSave(ERole.ROLE_MERCHANT);
        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);
        user.setRoles(List.of(roleMerchant, roleCustomer));
        userService.update(user);

        Merchant merchant = Merchant.builder()
                .user(user)
                .storeName(request.getStoreName())
                .address(request.getAddress())
                .description(request.getDescription())
                .status(Boolean.TRUE)
                .income(0D)
                .build();
        merchantRepository.save(merchant);

        return MerchantRegisterResponse.builder()
                .storeName(merchant.getStoreName())
                .createRequestDate(new Date())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Merchant getById(String id) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(id);
        if (optionalMerchant.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "merchant not found");
        return optionalMerchant.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<Merchant> getAll(PagingRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        return merchantRepository.findAll(pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Merchant update(UpdateMerchantRequest request) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(request.getId());
        if (optionalMerchant.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "merchant not found");
        Merchant merchant = optionalMerchant.get();
        merchant.setAddress(request.getAddress());
        merchant.setStoreName(request.getStoreName());
        merchant.setDescription(request.getDescription());
        return merchantRepository.save(merchant);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Merchant update(Merchant merchant) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(merchant.getId());
        if (optionalMerchant.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "merchant not found");
        return merchantRepository.save(merchant);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(id);
        if (optionalMerchant.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "merchant not found");
        merchantRepository.deleteById(id);
    }
}
