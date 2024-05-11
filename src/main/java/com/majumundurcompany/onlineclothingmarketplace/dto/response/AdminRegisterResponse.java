package com.majumundurcompany.onlineclothingmarketplace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRegisterResponse {
    private String username;
    private String role;
    private Date createdDate;
}
