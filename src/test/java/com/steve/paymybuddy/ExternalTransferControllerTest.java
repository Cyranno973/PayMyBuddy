package com.steve.paymybuddy.web.controller;

import com.steve.paymybuddy.dto.BankAccountDto;
import com.steve.paymybuddy.dto.ExternalTransferDto;
import com.steve.paymybuddy.dto.InternalTransferDto;
import com.steve.paymybuddy.service.BankAccountService;
import com.steve.paymybuddy.service.TransferService;
import com.steve.paymybuddy.service.UserService;
import com.steve.paymybuddy.web.exception.DataAlreadyExistException;
import com.steve.paymybuddy.web.exception.DataNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ExternalTransferControllerTest {

    @Autowired
    ExternalTransferController externalTransferController;
    @Mock
    BankAccountService bankAccountService;
    @Mock
    UserDetails userDetails;
    @Mock
    TransferService transferService;
    @Mock
    ExternalTransferDto externalTransferDto;
    @Mock
    Model model;
    @Mock
    BankAccountDto bankAccountDto;


    @Test
    void addBankAccount() {
//        when(bankAccountService.addBankAccount("emailTest", any())).thenReturn(any());
//        assertThrows(DataAlreadyExistException.class, () -> externalTransferController.addBankAccount(bankAccountDto, userDetails, redirectAttributes));
    }

    @Test
    void deleteBankAccount() {
        when(bankAccountService.deleteBankAccount("test")).thenReturn(true);
        String result = externalTransferController.deteleBankAccount("test");
        Assertions.assertThat(result).isEqualTo("redirect:/user/extransfer");
    }

    @Test
    void doExternalTransfer() {
//        when(transferService.doExternalTransfer(any())).thenReturn(new ExternalTransferDto());
//        String result = externalTransferController.doExternalTransfer(externalTransferDto, userDetails);
//        Assertions.assertThat(result).isEqualTo("redirect:/user/extransfer");
    }
}
