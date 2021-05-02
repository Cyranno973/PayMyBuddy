package com.steve.paymybuddy.dao;

import com.steve.paymybuddy.model.InternalTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternalTransferDao extends JpaRepository<InternalTransfer, Integer> {
    List<InternalTransfer> findAllByUserSender_EmailOrderByTransactionDateDesc(String emailOwner);
}
