package com.steve.paymybuddy.service.impl;

import com.steve.paymybuddy.dao.BankAccountDao;
import com.steve.paymybuddy.dao.UserDao;
import com.steve.paymybuddy.dto.BankAccountDto;
import com.steve.paymybuddy.model.BankAccount;
import com.steve.paymybuddy.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountDao bankAccountDao;
    private final UserDao userDao;

    @Autowired
    public BankAccountServiceImpl(BankAccountDao bankAccountDao,UserDao userDao) {
        this.bankAccountDao = bankAccountDao;
        this.userDao = userDao;
    }

    @Override
    public BankAccount addBankAccount(String emailOwner, BankAccountDto bankAccountDto) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban(bankAccountDto.getIban());
        bankAccount.setBic(bankAccountDto.getBic());
        bankAccount.setBankName(bankAccountDto.getBankName());
        bankAccount.setAccountName(bankAccountDto.getAccountName());
        bankAccount.setUser(userDao.findByEmail(emailOwner));

        bankAccountDao.save(bankAccount);
        return bankAccount;
    }

    @Override
    public List<BankAccount> findBankAccountByUser(String userEmail) {
        return bankAccountDao.findBankAccountsByUser_Email(userEmail);
    }

    @Override
    public Boolean deleteBankAccount(String iban) {
        if (bankAccountDao.existsById(iban)){
            bankAccountDao.deleteById(iban);
            return true;
        }
        return false;
    }
}
