package com.majumundurcompany.onlineclothingmarketplace.dto.response;

import com.majumundurcompany.onlineclothingmarketplace.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private String id;
    private String orderId;
    private Product product;
    private Double productPrice;
    private Integer quantity;
}
