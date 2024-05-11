package com.majumundurcompany.onlineclothingmarketplace.service;

import com.majumundurcompany.onlineclothingmarketplace.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetail createOrUpdate(OrderDetail orderDetail);
    List<OrderDetail> getAllByOrderId(String id);
}
