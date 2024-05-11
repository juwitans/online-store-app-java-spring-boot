package com.majumundurcompany.onlineclothingmarketplace.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchProductRequest {
    private Integer page;
    private Integer size;
    private String name;
    private Long minPrice;
    private Long maxPrice;
}
