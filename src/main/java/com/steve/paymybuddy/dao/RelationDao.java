package com.steve.paymybuddy.dao;

import com.steve.paymybuddy.model.BankAccount;
import com.steve.paymybuddy.model.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationDao extends JpaRepository<Relation, Integer> {
    Relation findByOwner_EmailAndBuddy_Email(String emailSender, String Receiver);
    List<Relation> findAllByOwner_Email(String emailOwner);
}
