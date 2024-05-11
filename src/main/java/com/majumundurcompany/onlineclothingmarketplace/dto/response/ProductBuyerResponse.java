package com.majumundurcompany.onlineclothingmarketplace.dto.response;

import com.majumundurcompany.onlineclothingmarketplace.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductBuyerResponse {
    private String username;
    private Date transDate;
    private String orderStatus;
}
