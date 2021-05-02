package com.steve.paymybuddy.dto;

import com.steve.paymybuddy.model.BankAccount;

import java.math.BigDecimal;

public class ExternalTransferDto {

    private Integer id;
    private String ibanUser;
    private BigDecimal amountUser;
    private String emailUser;
    private String description;
    private BigDecimal fees;

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIbanUser() {
        return ibanUser;
    }

    public void setIbanUser(String ibanUser) {
        this.ibanUser = ibanUser;
    }

    public BigDecimal getAmountUser() {
        return amountUser;
    }

    public void setAmountUser(BigDecimal amountUser) {
        this.amountUser = amountUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
