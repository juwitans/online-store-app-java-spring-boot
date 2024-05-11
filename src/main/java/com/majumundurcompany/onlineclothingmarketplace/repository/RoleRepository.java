package com.majumundurcompany.onlineclothingmarketplace.repository;

import com.majumundurcompany.onlineclothingmarketplace.constant.ERole;
import com.majumundurcompany.onlineclothingmarketplace.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(ERole role);
}
