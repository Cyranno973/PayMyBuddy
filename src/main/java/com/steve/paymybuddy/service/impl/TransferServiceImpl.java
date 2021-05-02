package com.steve.paymybuddy.service.impl;

import com.steve.paymybuddy.dao.InternalTransferDao;
import com.steve.paymybuddy.dao.RelationDao;
import com.steve.paymybuddy.dao.TransferDao;
import com.steve.paymybuddy.dao.UserDao;
import com.steve.paymybuddy.dto.ExternalTransferDto;
import com.steve.paymybuddy.dto.InternalTransferDto;
import com.steve.paymybuddy.model.InternalTransfer;
import com.steve.paymybuddy.model.Relation;
import com.steve.paymybuddy.service.TransferService;
import com.steve.paymybuddy.web.exception.DataNotExistException;
import com.steve.paymybuddy.web.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {

    private final TransferDao transferDao;
    private final UserDao userDao;
    private final RelationDao relationDao;
    private final InternalTransferDao internalTransferDao;

    @Autowired
    public TransferServiceImpl(TransferDao transferDao, UserDao userDao, RelationDao relationDao, InternalTransferDao internalTransferDao) {
        this.transferDao = transferDao;
        this.userDao = userDao;
        this.relationDao = relationDao;
        this.internalTransferDao = internalTransferDao;
    }

    @Override
    public InternalTransferDto doInternalTransfer(InternalTransferDto internalTransferDto) {
        //recuperation de la relation entre les 2 users (sa nous check aussi leur existence)
        Relation relation = relationDao.findByOwner_EmailAndBuddy_Email(internalTransferDto.getEmailSender(),internalTransferDto.getEmailReceiver());
        // on verifie leur amitié
        if (relation == null) {
            throw new DataNotFoundException("les 2 users ne sont pas ami");
        }
        // on check si le sender à assez d'argent pour la transaction
        if (internalTransferDto.getAmount().compareTo(relation.getOwner().getBalance())>0) {
            throw new DataNotExistException("fonds insuffisants");
        }
        InternalTransfer internalTransfer = new InternalTransfer();
        internalTransfer.setUserSender(relation.getOwner());
        internalTransfer.setUserReceiver(relation.getBuddy());
        internalTransfer.setStatus("Completed");
        internalTransfer.setAmount(internalTransferDto.getAmount());
        internalTransfer.setDescription(internalTransferDto.getDescription());
        internalTransfer.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));
        transferDao.save(internalTransfer);
        internalTransferDto.setId(internalTransfer.getId());
        // on met a jours les balance des 2 users

        relation.getOwner().setBalance(relation.getOwner().getBalance().subtract(internalTransferDto.getAmount()));
        relation.getBuddy().setBalance(relation.getBuddy().getBalance().add(internalTransferDto.getAmount()));
        userDao.save(relation.getOwner());
        userDao.save(relation.getBuddy());

        return internalTransferDto;
    }

    @Override
    public ExternalTransferDto doExternalTransfer(ExternalTransferDto externalTransferDto) {
        // récupérer le bank account en fontion de l'iban et de l'email du user
        // je check si il est present dans la base
        // calculer les fees (5%)
        // si montant > 0 => vérifier que la balance du user > (montant+fees) => balance = balance - montant
        // si montant < 0 => balance = balance + montant
        String email = externalTransferDto.getEmailUser();
        if (!userDao.existsByEmail(email)) {
            throw new DataNotFoundException("email non present");
        }
//        if (externalTransferDto.getIbanUser()) {
//
        return null;
//        }
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
}

