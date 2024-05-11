package com.majumundurcompany.onlineclothingmarketplace.repository;

import com.majumundurcompany.onlineclothingmarketplace.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    @Query(value = "SELECT * FROM t_order_detail t WHERE t.order_id = ?1", nativeQuery = true)
    List<OrderDetail> findAllByOrderId(String id);
}
