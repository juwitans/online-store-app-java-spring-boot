package com.majumundurcompany.onlineclothingmarketplace.repository;

import com.majumundurcompany.onlineclothingmarketplace.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, String> {
}
