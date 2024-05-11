package com.majumundurcompany.onlineclothingmarketplace.repository;

import com.majumundurcompany.onlineclothingmarketplace.constant.EOrderStatus;
import com.majumundurcompany.onlineclothingmarketplace.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, String> {
    Optional<OrderStatus> findByStatus(EOrderStatus status);
}
