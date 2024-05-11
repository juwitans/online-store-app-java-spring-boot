package com.majumundurcompany.onlineclothingmarketplace.repository;

import com.majumundurcompany.onlineclothingmarketplace.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @Query(value = "SELECT * FROM t_order t JOIN t_order_detail u ON u.order_id = t.order_id WHERE u.product_id = ?1", nativeQuery = true)
    List<Order> findAllByProductId(String productId);
}
