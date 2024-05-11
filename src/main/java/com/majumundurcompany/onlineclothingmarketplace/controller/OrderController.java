package com.majumundurcompany.onlineclothingmarketplace.controller;

import com.majumundurcompany.onlineclothingmarketplace.constant.EOrderStatus;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.OrderRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.PagingRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.*;
import com.majumundurcompany.onlineclothingmarketplace.entity.Order;
import com.majumundurcompany.onlineclothingmarketplace.entity.OrderDetail;
import com.majumundurcompany.onlineclothingmarketplace.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createNewTransaction(@RequestBody OrderRequest request) {
        Order order = orderService.create(request);

        OrderResponse orderResponse = getOrderResponse(order);

        WebResponse<OrderResponse> response = WebResponse.<OrderResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("successfully create new transaction")
                .data(orderResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable String id) {
        Order transaction = orderService.getById(id);

        OrderResponse orderResponse = getOrderResponse(transaction);

        WebResponse<OrderResponse> response = WebResponse.<OrderResponse>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("successfully get transaction by id")
                .data(orderResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllTransaction(@RequestParam(required = false, defaultValue = "1") Integer page,
                                               @RequestParam(required = false, defaultValue = "10") Integer size) {
        PagingRequest request = PagingRequest.builder()
                .page(page)
                .size(size)
                .build();
        List<OrderResponse> orderResponses = new ArrayList<>();
        Page<Order> orders = orderService.getAll(request);
        for (Order order : orders) {
            OrderResponse orderResponse = getOrderResponse(order);
            orderResponses.add(orderResponse);

        }

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(request.getPage())
                .size(request.getSize())
                .totalPages(orders.getTotalPages())
                .totalElements(orders.getTotalElements())
                .build();

        WebResponse<List<OrderResponse>> response = WebResponse.<List<OrderResponse>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("successfully get all transaction")
                .data(orderResponses)
                .paging(pagingResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MERCHANT')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String id, @RequestParam EOrderStatus status) {
        Order order = orderService.updateOrderStatus(id, status);
        OrderResponse orderResponse = getOrderResponse(order);
        WebResponse<OrderResponse> response = WebResponse.<OrderResponse>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("successfully update order status")
                .data(orderResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path = "/payment/{id}")
    public ResponseEntity<?> updatePaymentStatus(@PathVariable String id) {
        Order order = orderService.updatePaymentStatus(id);
        OrderResponse orderResponse = getOrderResponse(order);
        WebResponse<OrderResponse> response = WebResponse.<OrderResponse>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("payment success")
                .data(orderResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/products/{id}")
    @PreAuthorize("hasAnyRole('MERCHANT','ADMIN')")
    public ResponseEntity<?> getCustomersByProductId(@PathVariable String id) {
        List<ProductBuyerResponse> buyerResponseList = orderService.getAllByProductId(id);
        WebResponse<List<ProductBuyerResponse>> response = WebResponse.<List<ProductBuyerResponse>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Successfully get the buyers list")
                .data(buyerResponseList)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private static OrderResponse getOrderResponse(Order transaction) {
        List<OrderDetailResponse> orderDetailList = new ArrayList<>();
        for (OrderDetail orderDetail : transaction.getOrderDetails()) {
            OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                    .id(orderDetail.getId())
                    .orderId(transaction.getId())
                    .product(orderDetail.getProduct())
                    .productPrice(orderDetail.getPrice())
                    .quantity(orderDetail.getQuantity())
                    .build();

            orderDetailList.add(orderDetailResponse);
        }

        OrderResponse orderResponse = OrderResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .transDate(transaction.getTransactionDate())
                .orderDetails(orderDetailList)
                .build();
        return orderResponse;
    }
}
