package com.majumundurcompany.onlineclothingmarketplace.controller;

import com.majumundurcompany.onlineclothingmarketplace.dto.request.NewBankAccountRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.WebResponse;
import com.majumundurcompany.onlineclothingmarketplace.entity.BankAccount;
import com.majumundurcompany.onlineclothingmarketplace.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/merchants/banks")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @PostMapping
    @PreAuthorize("hasAnyRole('MERCHANT')")
    public ResponseEntity<?> createNew(@RequestBody NewBankAccountRequest request) {
        BankAccount bankAccount = bankAccountService.create(request);
        WebResponse<BankAccount> response = WebResponse.<BankAccount>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("successfully create bank account data")
                .data(bankAccount)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/:{id}")
    @PreAuthorize("hasAnyRole('MERCHANT','ADMIN')")
    public ResponseEntity<?> getAllAccountsByMerchantId(@PathVariable String id) {
        List<BankAccount> bankAccounts = bankAccountService.getAllByMerchantId(id);

        WebResponse<List<BankAccount>> response = WebResponse.<List<BankAccount>>builder()
                .message("successfully get bank accounts")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(bankAccounts)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('MERCHANT','ADMIN')")
    public ResponseEntity<?> getBankAccountById(@PathVariable String id) {
        BankAccount bankAccount = bankAccountService.getById(id);
        WebResponse<BankAccount> response = WebResponse.<BankAccount>builder()
                .message("successfully get bank account")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(bankAccount)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('MERCHANT','ADMIN')")
    public ResponseEntity<?> deleteBankAccount(@PathVariable String id) {
        bankAccountService.deleteById(id);
        WebResponse<BankAccount> response = WebResponse.<BankAccount>builder()
                .message("successfully delete bank account")
                .status(HttpStatus.OK.getReasonPhrase())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('MERCHANT')")
    public ResponseEntity<?> updateBankAccount(@RequestBody BankAccount account) {
        BankAccount update = bankAccountService.update(account);
        WebResponse<BankAccount> response = WebResponse.<BankAccount>builder()
                .message("successfully update bank account")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(update)
                .build();
        return ResponseEntity.ok(response);
    }
}
