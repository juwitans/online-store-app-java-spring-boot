package com.majumundurcompany.onlineclothingmarketplace.service;

import com.majumundurcompany.onlineclothingmarketplace.dto.request.MerchantRegisterRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.PagingRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.UpdateMerchantRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.MerchantRegisterResponse;
import com.majumundurcompany.onlineclothingmarketplace.entity.Merchant;
import org.springframework.data.domain.Page;

public interface MerchantService {
    MerchantRegisterResponse create(MerchantRegisterRequest merchant);
    Merchant getById(String id);
    Page<Merchant> getAll(PagingRequest request);
    Merchant update(UpdateMerchantRequest request);
    Merchant update(Merchant merchant);
    void deleteById(String id);
}
