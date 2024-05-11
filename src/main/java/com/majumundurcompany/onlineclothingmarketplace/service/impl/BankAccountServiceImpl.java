package com.majumundurcompany.onlineclothingmarketplace.service.impl;

import com.majumundurcompany.onlineclothingmarketplace.dto.request.NewBankAccountRequest;
import com.majumundurcompany.onlineclothingmarketplace.entity.BankAccount;
import com.majumundurcompany.onlineclothingmarketplace.entity.Merchant;
import com.majumundurcompany.onlineclothingmarketplace.repository.BankAccountRepository;
import com.majumundurcompany.onlineclothingmarketplace.service.BankAccountService;
import com.majumundurcompany.onlineclothingmarketplace.service.MerchantService;
import com.majumundurcompany.onlineclothingmarketplace.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final MerchantService merchantService;
    private final ValidationUtil validationUtil;

    @Override
    public BankAccount create(NewBankAccountRequest request) {
        validationUtil.validate(request);
        Merchant merchant = merchantService.getById(request.getMerchantId());
        BankAccount bankAccount = BankAccount.builder()
                .merchant(merchant)
                .bankName(request.getBankName())
                .number(request.getNumber())
                .build();
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<BankAccount> getAllByMerchantId(String id) {
        return bankAccountRepository.findAllByMerchantId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BankAccount getById(String id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bank account not found"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BankAccount update(BankAccount account) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(account.getId());
        if (optionalBankAccount.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "bank account not found");
        return bankAccountRepository.save(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(id);
        if (optionalBankAccount.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "bank account not found");
        bankAccountRepository.deleteById(id);
    }
}
