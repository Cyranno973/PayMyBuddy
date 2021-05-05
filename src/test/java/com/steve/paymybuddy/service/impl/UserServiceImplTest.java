package com.steve.paymybuddy.service.impl;

import com.steve.paymybuddy.dao.RelationDao;
import com.steve.paymybuddy.dao.RoleDao;
import com.steve.paymybuddy.dao.UserDao;
import com.steve.paymybuddy.dto.UserRegistrationDto;
import com.steve.paymybuddy.model.Relation;
import com.steve.paymybuddy.model.Role;
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
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;
    @MockBean
    UserDao userDao;
    @MockBean
    RelationDao relationDao;
    @MockBean
    RoleDao roleDao;

    User owner = new User(1, "barack", "obama", "a@a.com", "1234", BigDecimal.ZERO, Timestamp.valueOf(LocalDateTime.now()));
    User buddy = new User(2, "george", "bush", "b@b.com", "4321", BigDecimal.ZERO, Timestamp.valueOf(LocalDateTime.now()));

    Relation relation = new Relation(owner, buddy);

    Role role = new Role("USER");

    UserRegistrationDto userRegistrationDto = new UserRegistrationDto("barack", "obama", "a@a.com", "1234");


    @Test
    void listEmailRelation() {
        when(relationDao.findAllByOwner_Email(any())).thenReturn(Arrays.asList(relation));
        userService.listEmailRelation(any());
        verify(relationDao, times(1)).findAllByOwner_Email(any());
    }

    @Test
    void addBuddy() {
        try {
            userService.addBuddy(relation.getOwner().getEmail(), relation.getBuddy().getEmail());
            verify(userDao, times(2)).findByEmail(any());
            verify(relationDao, times(0)).save(any());

        } catch (DataNotFoundException | SQLException e){
            assert(e.getMessage().contains("Cette personne n'existe pas"));
        }
    }

    @Test
    void deleteRelation() {
        when(relationDao.existsById(any())).thenReturn(true);
        userService.deleteRelation(owner.getId());
        verify(relationDao, times(1)).deleteById(any());
    }

    @Test
    void save() throws SQLException {
        when(roleDao.findRoleByName("USER")).thenReturn(role);
        userService.save(userRegistrationDto);
        verify(roleDao, times(1)).findRoleByName(any());
    }

}
