package com.majumundurcompany.onlineclothingmarketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewBankAccountRequest {
    @NotBlank
    private String merchantId;
    @NotBlank
    @PositiveOrZero
    private Long number;
    @NotBlank
    private String bankName;
}
