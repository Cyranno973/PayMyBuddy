package com.steve.paymybuddy.service.impl;

import com.steve.paymybuddy.dao.*;
import com.steve.paymybuddy.dto.ExternalTransferDto;
import com.steve.paymybuddy.dto.InternalTransferDto;
import com.steve.paymybuddy.model.*;
import com.steve.paymybuddy.service.TransferService;
import com.steve.paymybuddy.web.exception.DataNotExistException;
import com.steve.paymybuddy.web.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class TransferServiceImpl implements TransferService {

    private final TransferDao transferDao;
    private final UserDao userDao;
    private final RelationDao relationDao;
    private final InternalTransferDao internalTransferDao;
    private final ExternalTransferDao externalTransferDao;
    private final BankAccountDao bankAccountDao;

    @Autowired
    public TransferServiceImpl(TransferDao transferDao, UserDao userDao, RelationDao relationDao, InternalTransferDao internalTransferDao, ExternalTransferDao externalTransferDao, BankAccountDao bankAccountDao) {
        this.transferDao = transferDao;
        this.userDao = userDao;
        this.relationDao = relationDao;
        this.internalTransferDao = internalTransferDao;
        this.externalTransferDao = externalTransferDao;
        this.bankAccountDao = bankAccountDao;
    }

    @Override

    public InternalTransferDto doInternalTransfer(InternalTransferDto internalTransferDto) throws SQLException {
        //recuperation de la relation entre les 2 users (sa nous check aussi leur existence)
        Relation relation = relationDao.findByOwner_EmailAndBuddy_Email(internalTransferDto.getEmailSender(), internalTransferDto.getEmailReceiver());
        // on verifie leur amitié
        if (relation == null) {
            throw new DataNotFoundException("les 2 users ne sont pas ami");
        }
        // on check si le sender à assez d'argent pour la transaction
        if (internalTransferDto.getAmount().compareTo(relation.getOwner().getBalance()) > 0) {
            throw new DataNotExistException("fonds insuffisants");
        }
        InternalTransfer internalTransfer = new InternalTransfer();
        internalTransfer.setUserSender(relation.getOwner());
        internalTransfer.setUserReceiver(relation.getBuddy());
        internalTransfer.setStatus("Completed");
        internalTransfer.setAmount(internalTransferDto.getAmount());
        internalTransfer.setDescription(internalTransferDto.getDescription());
        internalTransfer.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));

        try {
            transferDao.save(internalTransfer);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new SQLException("Probleme save internal transfert");
        }

        internalTransferDto.setId(internalTransfer.getId());
        // on met a jours les balance des 2 users

        relation.getOwner().setBalance(relation.getOwner().getBalance().subtract(internalTransferDto.getAmount()));
        relation.getBuddy().setBalance(relation.getBuddy().getBalance().add(internalTransferDto.getAmount()));
        relation.setBuddy(new User());

        try {
            userDao.save(relation.getOwner());
            userDao.save(relation.getBuddy());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new SQLException("Probleme save internal transfert");
        }
        return internalTransferDto;
    }

    @Override
    public ExternalTransferDto doExternalTransfer(ExternalTransferDto externalTransferDto) throws SQLException {
        // récupérer le bank account en fontion de l'iban et de l'email du user
        BankAccount bankAccount = bankAccountDao.findBankAccountByIbanAndUser_Email(externalTransferDto.getIbanUser(), externalTransferDto.getEmailUser());
        User user = bankAccount.getUser();
        // On attribut le dernier iban ajouté.
        // Fees
        BigDecimal fee = externalTransferDto.getAmountUser().multiply(BigDecimal.valueOf(0.005));

        ExternalTransfer externalTransfer = new ExternalTransfer();
        externalTransfer.setAmount(externalTransferDto.getAmountUser());
        externalTransfer.setDescription(externalTransferDto.getDescription());
        externalTransfer.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));
        externalTransfer.setStatus("COMPLETED");
        externalTransfer.setFees(fee);
        externalTransfer.setBankAccount(bankAccount);

        try {
            transferDao.save(externalTransfer);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new SQLException("Probleme save external transfert");
        }

        externalTransferDto.setId(externalTransfer.getId());
        user.setBalance(user.getBalance().add(externalTransfer.getAmount().subtract(fee)));

        try {
            userDao.save(user);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new SQLException("Probleme save external transfert");
        }

        return externalTransferDto;
    }

    @Override
    public List<InternalTransferDto> findInternalTransferByUser(String emailOwner) {
        List<InternalTransferDto> internalTransferDtos = new ArrayList<>();
        for (InternalTransfer internalTransfer : internalTransferDao.findAllByUserSender_EmailOrderByTransactionDateDesc(emailOwner)) {
            InternalTransferDto dto = new InternalTransferDto();
            dto.setEmailSender(internalTransfer.getUserSender().getEmail());
            dto.setEmailReceiver(internalTransfer.getUserReceiver().getEmail());
            dto.setAmount(internalTransfer.getAmount());
            dto.setId(internalTransfer.getId());
            dto.setDescription(internalTransfer.getDescription());
            internalTransferDtos.add(dto);
        }
        return internalTransferDtos;
    }

    @Override
    public List<ExternalTransferDto> findExternalTransferByUser(String emailOwner) {
        List<ExternalTransferDto> externalTransferDtos = new ArrayList<>();
        for (ExternalTransfer externalTransfer : externalTransferDao.findAllByBankAccount_User_EmailOrderByTransactionDateDesc(emailOwner)) {
            ExternalTransferDto dto = new ExternalTransferDto();
            dto.setIbanUser(externalTransfer.getBankAccount().getIban());
            dto.setDescription(externalTransfer.getDescription());
            dto.setAmountUser(externalTransfer.getAmount());
            dto.setFees(externalTransfer.getFees());
            externalTransferDtos.add(dto);
        }
        return externalTransferDtos;
    }
}

