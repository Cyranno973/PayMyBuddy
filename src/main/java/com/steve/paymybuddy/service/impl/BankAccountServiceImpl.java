package com.steve.paymybuddy.service.impl;

import com.steve.paymybuddy.dao.BankAccountDao;
import com.steve.paymybuddy.dao.UserDao;
import com.steve.paymybuddy.dto.BankAccountDto;
import com.steve.paymybuddy.model.BankAccount;
import com.steve.paymybuddy.model.User;
import com.steve.paymybuddy.service.BankAccountService;
import com.steve.paymybuddy.web.exception.DataAlreadyExistException;
import com.steve.paymybuddy.web.exception.DataMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountDao bankAccountDao;
    private final UserDao userDao;

    @Autowired
    public BankAccountServiceImpl(BankAccountDao bankAccountDao, UserDao userDao) {
        this.bankAccountDao = bankAccountDao;
        this.userDao = userDao;
    }

    @Override
    public BankAccount addBankAccount(String emailOwner, BankAccountDto bankAccountDto) {
        if (bankAccountDto.getIban().isBlank()) {
            throw new DataMissingException("L'iban ne peut pas être vide !!");
        }

        User user = userDao.findByEmail(emailOwner);

        String iban = bankAccountDto.getIban();
        BankAccount bankAccount = bankAccountDao.findBankAccountByIban(iban);

        if (bankAccount == null) {
            bankAccount = new BankAccount();
            bankAccount.setIban(bankAccountDto.getIban());
            bankAccount.setBic(bankAccountDto.getBic());
            bankAccount.setBankName(bankAccountDto.getBankName());
            bankAccount.setAccountName(bankAccountDto.getAccountName());
            bankAccount.setUser(user);
            bankAccountDao.save(bankAccount);
            return bankAccount;
        } else if (bankAccount.getUser().equals(user)) {
            throw new DataAlreadyExistException("Vous possedez deja ce compte bancaire !");
        } else {
            throw new DataAlreadyExistException("Ce compte bancaire appartient à un autre utilisateur !");
        }
    }

    @Override
    public List<BankAccount> findBankAccountByUser(String userEmail) {
        return bankAccountDao.findBankAccountsByUser_Email(userEmail);
    }

    @Override
    public Boolean deleteBankAccount(String iban) {
        if (bankAccountDao.existsById(iban)) {
            bankAccountDao.deleteById(iban);
            return true;
        }
        return false;
    }
}
