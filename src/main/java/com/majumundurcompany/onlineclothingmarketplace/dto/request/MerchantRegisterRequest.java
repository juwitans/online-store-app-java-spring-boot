package com.majumundurcompany.onlineclothingmarketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantRegisterRequest {
    @NotBlank
    private String userId;
    @NotBlank
    @Size(min = 5, max = 20)
    private String storeName;
    @NotBlank
    private String address;
    private String description;
}
