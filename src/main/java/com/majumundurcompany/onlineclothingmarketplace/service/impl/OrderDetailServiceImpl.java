package com.majumundurcompany.onlineclothingmarketplace.service.impl;

import com.majumundurcompany.onlineclothingmarketplace.entity.OrderDetail;
import com.majumundurcompany.onlineclothingmarketplace.repository.OrderDetailRepository;
import com.majumundurcompany.onlineclothingmarketplace.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDetail createOrUpdate(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetail> getAllByOrderId(String id) {
        return orderDetailRepository.findAllByOrderId(id);
    }
}
