package com.majumundurcompany.onlineclothingmarketplace.controller;

import com.majumundurcompany.onlineclothingmarketplace.dto.request.MerchantRegisterRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.PagingRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.UpdateMerchantRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.MerchantRegisterResponse;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.PagingResponse;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.WebResponse;
import com.majumundurcompany.onlineclothingmarketplace.entity.Merchant;
import com.majumundurcompany.onlineclothingmarketplace.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/merchants")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    @PostMapping
    @PreAuthorize("hasAnyRole('MERCHANT')")
    public ResponseEntity<?> createNew(@RequestBody MerchantRegisterRequest request) {
        MerchantRegisterResponse registerResponse = merchantService.create(request);
        WebResponse<MerchantRegisterResponse> response = WebResponse.<MerchantRegisterResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("successfully send create merchant request")
                .data(registerResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getAllMerchants(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PagingRequest request = PagingRequest.builder()
                .page(page)
                .size(size)
                .build();
        Page<Merchant> merchants = merchantService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(request.getPage())
                .size(request.getSize())
                .build();

        WebResponse<List<Merchant>> response = WebResponse.<List<Merchant>>builder()
                .message("successfully get merchants")
                .status(HttpStatus.OK.getReasonPhrase())
                .paging(pagingResponse)
                .data(merchants.getContent())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getMerchantById(@PathVariable String id) {
        Merchant merchant = merchantService.getById(id);
        WebResponse<Merchant> response = WebResponse.<Merchant>builder()
                .message("successfully get merchant")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(merchant)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id) {
        merchantService.deleteById(id);
        WebResponse<Merchant> response = WebResponse.<Merchant>builder()
                .message("successfully delete merchant")
                .status(HttpStatus.OK.getReasonPhrase())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> updateMerchant(@RequestBody UpdateMerchantRequest request) {
        Merchant merchant = merchantService.update(request);
        WebResponse<Merchant> response = WebResponse.<Merchant>builder()
                .message("successfully update merchant")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(merchant)
                .build();
        return ResponseEntity.ok(response);
    }
}
