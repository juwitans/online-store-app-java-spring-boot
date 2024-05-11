package com.majumundurcompany.onlineclothingmarketplace.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private String customerId;
    private String merchantId;
    private String address;
    private List<OrderDetailRequest> orderDetails;
}
