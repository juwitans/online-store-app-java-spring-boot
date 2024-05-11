package com.majumundurcompany.onlineclothingmarketplace.service.impl;

import com.majumundurcompany.onlineclothingmarketplace.constant.EOrderStatus;
import com.majumundurcompany.onlineclothingmarketplace.entity.OrderStatus;
import com.majumundurcompany.onlineclothingmarketplace.repository.OrderStatusRepository;
import com.majumundurcompany.onlineclothingmarketplace.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderStatus getOrSave(EOrderStatus status) {
        Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findByStatus(status);
        if (optionalOrderStatus.isPresent()) return optionalOrderStatus.get();

        OrderStatus orderStatus = OrderStatus.builder()
                .status(status)
                .build();
        orderStatusRepository.saveAndFlush(orderStatus);
        return orderStatus;
    }
}
