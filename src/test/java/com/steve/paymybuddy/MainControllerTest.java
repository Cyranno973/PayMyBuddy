package com.steve.paymybuddy.web.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    MainController mainController;

    @Test
    void login() {
        String result = mainController.login();
        Assertions.assertThat(result).isEqualTo("login");
    }

    @Test
    void home() {
        String result = mainController.home();
        Assertions.assertThat(result).isEqualTo("index");
    }
}
