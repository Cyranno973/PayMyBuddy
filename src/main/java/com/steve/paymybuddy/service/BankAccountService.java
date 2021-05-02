package com.steve.paymybuddy.service;

import com.steve.paymybuddy.dto.BankAccountDto;
import com.steve.paymybuddy.model.BankAccount;

import java.util.List;

public interface BankAccountService {
    /**
     * permet d'ajouter un bankaccount
     * @param emailOwner
     * @param bankAccountDto
     * @return
     */
    BankAccount addBankAccount(String emailOwner, BankAccountDto bankAccountDto);

    /**
     * retourne une liste de bank account de l'utilisateur
     * @param username
     * @return
     */
    List<BankAccount> findBankAccountByUser(String username);

    /**
     * permet de supprimer un bankaccount
     * @param iban
     * @return
     */
    Boolean deleteBankAccount(String iban);

}
