package com.majumundurcompany.onlineclothingmarketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewProductRequest {
    @NotBlank(message = "You must include merchant id")
    private String merchantId;
    @NotBlank(message = "product name must be filled")
    @Size(min = 5, max = 40)
    private String name;
    @PositiveOrZero
    private Double price;
    private String description;
    @PositiveOrZero
    private int stock;
}
