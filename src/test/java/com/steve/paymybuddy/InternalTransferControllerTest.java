package com.steve.paymybuddy.web.controller;

import com.steve.paymybuddy.dto.InternalTransferDto;
import com.steve.paymybuddy.model.Relation;
import com.steve.paymybuddy.service.TransferService;
import com.steve.paymybuddy.service.UserService;
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

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class InternalTransferControllerTest {

    @Autowired
    InternalTransferController internalTransferController;
    @Mock
    private TransferService transferService;
    @Mock
    private UserService userService;
    @Mock
    UserDetails userDetails;
    @Mock
    RedirectAttributes redirectAttributes;
    @Mock
    Model model;
    @Mock
    InternalTransferDto internalTransferDto;

    @Test
    void transferPage() {
        when(userService.listEmailRelation("email1")).thenReturn(Arrays.asList(new Relation()));
        when(transferService.findInternalTransferByUser("email1")).thenReturn(Arrays.asList(new InternalTransferDto()));
        String result = internalTransferController.transferPage(model, userDetails);
        Assertions.assertThat(result).isEqualTo("transfer2");
    }

    @Test
    void internalTransfer() throws SQLException {
        when(transferService.doInternalTransfer(internalTransferDto)).thenReturn(internalTransferDto);
        assertThrows(DataNotFoundException.class, () -> internalTransferController.internalTransfer(internalTransferDto, userDetails, redirectAttributes));
    }
}
