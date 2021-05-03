package com.steve.paymybuddy.web.controller;

import com.steve.paymybuddy.model.Relation;
import com.steve.paymybuddy.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.Arrays;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class RelationControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RelationController relationController;
    @MockBean
    UserService userService;
    @Mock
    UserDetails userDetails;
    @Mock
    RedirectAttributes redirectAttributes;
    @Mock
    Model model;

    @Test
    void relationPage() {
        when(userService.listEmailRelation("email1")).thenReturn(Arrays.asList(new Relation()));
        String result = relationController.relationPage(model, userDetails);
        Assertions.assertThat(result).isEqualTo("relation");
    }

    @Test
    void addRelation() throws SQLException {
        when(userService.addBuddy("email1","email2")).thenReturn(new Relation());
        String result = relationController.addRelation("email1", userDetails, redirectAttributes);
        Assertions.assertThat(result).isEqualTo("redirect:/user/relation");
    }

    @Test
    void deleteRelation() throws Exception {
        when(userService.deleteRelation(1)).thenReturn(true);
        String result = relationController.deleteRelation(1);
        Assertions.assertThat(result).isEqualTo("redirect:/user/relation");
    }
}
