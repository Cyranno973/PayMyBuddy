package com.steve.paymybuddy.dao;

import com.steve.paymybuddy.model.ExternalTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExternalTransferDao extends JpaRepository<ExternalTransfer, Integer> {

    List<ExternalTransfer> findAllByBankAccount_User_EmailOrderByTransactionDateDesc(String emailOwner);
}
