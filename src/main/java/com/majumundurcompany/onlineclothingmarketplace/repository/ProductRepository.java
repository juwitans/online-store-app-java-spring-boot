package com.majumundurcompany.onlineclothingmarketplace.repository;

import com.majumundurcompany.onlineclothingmarketplace.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    @Query(value = "SELECT * FROM m_product m WHERE m.merchant_id = ?1", nativeQuery = true)
    List<Product> findAllByMerchantId(String id);
}
