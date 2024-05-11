package com.majumundurcompany.onlineclothingmarketplace.service.impl;

import com.majumundurcompany.onlineclothingmarketplace.constant.ERole;
import com.majumundurcompany.onlineclothingmarketplace.entity.Role;
import com.majumundurcompany.onlineclothingmarketplace.repository.RoleRepository;
import com.majumundurcompany.onlineclothingmarketplace.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role getOrSave(ERole role) {
        Optional<Role> optionalRole = roleRepository.findByRole(role);
        if (optionalRole.isPresent()) return optionalRole.get();

        Role newRole = Role.builder()
                .role(role)
                .build();
        roleRepository.saveAndFlush(newRole);
        return newRole;
    }
}
