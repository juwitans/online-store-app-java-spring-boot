package com.majumundurcompany.onlineclothingmarketplace.service;

import com.majumundurcompany.onlineclothingmarketplace.constant.EOrderStatus;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.OrderRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.PagingRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.ProductBuyerResponse;
import com.majumundurcompany.onlineclothingmarketplace.entity.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    Order create(OrderRequest request);
    Page<Order> getAll(PagingRequest request);
    Order getById(String id);
    Order updateOrderStatus(String orderId, EOrderStatus orderStatus);
    Order updatePaymentStatus(String orderId);
    List<ProductBuyerResponse> getAllByProductId(String productId);
}
