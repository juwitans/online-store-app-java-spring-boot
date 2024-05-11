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
public class UpdateMerchantRequest {
    @NotBlank
    private String id;
    @NotBlank(message = "store name must not be blank")
    @Size(min = 5, max = 20)
    private String storeName;
    @NotBlank
    private String address;
    private String description;
}
