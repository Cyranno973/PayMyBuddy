package com.steve.paymybuddy.web.controller;

import com.steve.paymybuddy.dto.UserRegistrationDto;
import com.steve.paymybuddy.model.User;
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

import static  org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RegistrationController registrationController;
    @MockBean
    UserService userService;
    @Mock
    UserRegistrationDto userRegistrationDto;

    @Mock
    RedirectAttributes redirectAttributes;

    @Test
    void showRegistrationForm() {
        when(userService.deleteRelation(1)).thenReturn(true);
        String result = registrationController.showRegistrationForm();
        Assertions.assertThat(result).isEqualTo("registration");
    }

    @Test
    void registerUserAccount() throws SQLException {
        when(userService.save(any())).thenReturn(new User());
        String result = registrationController.registerUserAccount(userRegistrationDto, redirectAttributes);
        Assertions.assertThat(result).isEqualTo("redirect:/registration?success");
    }
}
