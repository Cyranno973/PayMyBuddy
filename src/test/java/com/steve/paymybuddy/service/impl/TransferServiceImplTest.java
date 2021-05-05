package com.steve.paymybuddy.service.impl;

import com.steve.paymybuddy.dao.*;
import com.steve.paymybuddy.dto.InternalTransferDto;
import com.steve.paymybuddy.dto.UserRegistrationDto;
import com.steve.paymybuddy.model.Relation;
import com.steve.paymybuddy.model.Role;
import com.steve.paymybuddy.model.Transfer;
import com.steve.paymybuddy.model.User;
import com.steve.paymybuddy.web.exception.DataNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TransferServiceImplTest {

    @Autowired
    TransferServiceImpl transferService;
    @MockBean
    UserDao userDao;
    @MockBean
    RelationDao relationDao;
    @MockBean
    TransferDao transferDao;
    @MockBean
    InternalTransferDao internalTransferDao;
    @MockBean
    ExternalTransferDao externalTransferDao;
    @MockBean
    BankAccountDao bankAccountDao;

    User owner = new User(1, "barack", "obama", "a@a.com", "1234", BigDecimal.ZERO, Timestamp.valueOf(LocalDateTime.now()));
    User buddy = new User(2, "george", "bush", "b@b.com", "4321", BigDecimal.ZERO, Timestamp.valueOf(LocalDateTime.now()));

    Relation relation = new Relation(owner, buddy);

    Transfer transfer = new Transfer();

    Role role = new Role("USER");

    InternalTransferDto internalTransferDto = new InternalTransferDto();
    UserRegistrationDto userRegistrationDto = new UserRegistrationDto("barack", "obama", "a@a.com", "1234");


    @Test
    void doInternalTransfer() throws SQLException {
        try {
            transferService.doInternalTransfer(internalTransferDto);
            verify(transferDao, times(0)).save(transfer);
            verify(userDao, times(0)).save(owner);
            verify(relationDao, times(1)).findByOwner_EmailAndBuddy_Email(any(),any());

        } catch (DataNotFoundException e){
            assert(e.getMessage().contains("les 2 users ne sont pas ami"));
        }
    }

    @Test
    void doExternalTransfer() throws SQLException {
        transferService.doInternalTransfer(internalTransferDto);
        verify(transferDao, times(0)).save(transfer);
        verify(userDao, times(0)).save(owner);
        verify(relationDao, times(1)).findByOwner_EmailAndBuddy_Email(any(),any());
    }

    @Test
    void findInternalTransferByUser() {
    }

    @Test
    void findExternalTransferByUser() {
    }
}
