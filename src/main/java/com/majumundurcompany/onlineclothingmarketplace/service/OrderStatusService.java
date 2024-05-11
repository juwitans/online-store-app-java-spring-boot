package com.majumundurcompany.onlineclothingmarketplace.service;

import com.majumundurcompany.onlineclothingmarketplace.constant.EOrderStatus;
import com.majumundurcompany.onlineclothingmarketplace.entity.OrderStatus;

public interface OrderStatusService {
    OrderStatus getOrSave(EOrderStatus status);
}
