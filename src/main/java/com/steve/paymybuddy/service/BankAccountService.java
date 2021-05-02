package com.steve.paymybuddy.service;

import com.steve.paymybuddy.dto.BankAccountDto;
import com.steve.paymybuddy.model.BankAccount;

import java.util.List;

public interface BankAccountService {
    BankAccount addBankAccount(String emailOwner, BankAccountDto bankAccountDto);
    List<BankAccount> findBankAccountByUser(String username);
    Boolean deleteBankAccount(String iban);

}
