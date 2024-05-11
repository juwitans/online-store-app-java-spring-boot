package com.majumundurcompany.onlineclothingmarketplace.service.impl;

import com.majumundurcompany.onlineclothingmarketplace.constant.EOrderStatus;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.OrderDetailRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.OrderRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.PagingRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.ProductBuyerResponse;
import com.majumundurcompany.onlineclothingmarketplace.entity.*;
import com.majumundurcompany.onlineclothingmarketplace.repository.OrderRepository;
import com.majumundurcompany.onlineclothingmarketplace.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final OrderStatusService orderStatusService;
    private final ProductService productService;
    private final OrderDetailService orderDetailService;
    private final MerchantService merchantService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order create(OrderRequest request) {
        Customer customer = customerService.getById(request.getCustomerId());
        Merchant merchant = merchantService.getById(request.getMerchantId());
        OrderStatus orderStatus = orderStatusService.getOrSave(EOrderStatus.STATUS_PENDING);
        Order order = Order.builder()
                .customer(customer)
                .merchant(merchant)
                .address(request.getAddress())
                .transactionDate(new Date())
                .paymentStatus(Boolean.FALSE)
                .status(orderStatus)
                .build();
        orderRepository.saveAndFlush(order);

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()) {
            Product product = productService.getById(orderDetailRequest.getProductId());
            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .product(product)
                    .price(product.getPrice())
                    .quantity(orderDetailRequest.getQuantity())
                    .build();

            if (product.getStock() - orderDetailRequest.getQuantity() < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity exceeds stock");
            }

            product.setStock(product.getStock() - orderDetailRequest.getQuantity());
            productService.update(product);
            orderDetails.add(orderDetailService.createOrUpdate(orderDetail));
        }

        order.setOrderDetails(orderDetails);
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<Order> getAll(PagingRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        return orderRepository.findAll(pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order getById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order updateOrderStatus(String orderId, EOrderStatus orderStatus) {
        OrderStatus orderStatus1 = orderStatusService.getOrSave(orderStatus);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found");
        Order order = optionalOrder.get();
        order.setStatus(orderStatus1);

        if (orderStatus == EOrderStatus.STATUS_DELIVERED) {
            Customer customer = customerService.getById(order.getCustomer().getId());
            customer.setPoint(+1);
            customerService.update(customer);

            Merchant merchant = merchantService.getById(order.getMerchant().getId());
            Double income = 0D;
            for (OrderDetail orderDetail : orderDetailService.getAllByOrderId(orderId)) {
                income += orderDetail.getPrice();
            }
            merchant.setIncome(merchant.getIncome()+income);
            merchantService.update(merchant);
        }

        return orderRepository.saveAndFlush(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order updatePaymentStatus(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found");
        Order order = optionalOrder.get();
        order.setPaymentStatus(Boolean.TRUE);
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public List<ProductBuyerResponse> getAllByProductId(String productId) {
        List<Order> customersByProductId = orderRepository.findAllByProductId(productId);
        List<ProductBuyerResponse> responseList = new ArrayList<>();
        for (Order order : customersByProductId) {
            ProductBuyerResponse response = ProductBuyerResponse.builder()
                    .username(order.getCustomer().getUser().getUsername())
                    .orderStatus(order.getStatus().getStatus().name())
                    .transDate(order.getTransactionDate())
                    .build();
            responseList.add(response);
        }
        return responseList;
    }
}
