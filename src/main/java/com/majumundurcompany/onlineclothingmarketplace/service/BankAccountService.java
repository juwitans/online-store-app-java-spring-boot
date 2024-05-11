package com.majumundurcompany.onlineclothingmarketplace.service;

import com.majumundurcompany.onlineclothingmarketplace.dto.request.NewBankAccountRequest;
import com.majumundurcompany.onlineclothingmarketplace.entity.BankAccount;

import java.util.List;

public interface BankAccountService {
    BankAccount create(NewBankAccountRequest bankAccount);
    List<BankAccount> getAllByMerchantId(String id);
    BankAccount getById(String id);
    BankAccount update(BankAccount account);
    void deleteById(String id);
}
