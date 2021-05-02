package com.steve.paymybuddy.dao;

import com.steve.paymybuddy.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountDao extends JpaRepository<BankAccount, String> {
    BankAccount findBankAccountByIban(String iban);
    BankAccount findBankAccountByIbanAndUser_Email(String iban, String emailUser);
    List<BankAccount> findBankAccountsByUser_Email(String emailUser);

}
