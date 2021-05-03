package com.steve.paymybuddy.web.controller;

import com.steve.paymybuddy.dto.UserRegistrationDto;
import com.steve.paymybuddy.service.UserService;
import com.steve.paymybuddy.web.exception.DataNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

   final private UserService userService;

    public RegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto, RedirectAttributes redirectAttributes) throws SQLException {
        try{
            userService.save(registrationDto);
        } catch (DataNotExistException | SQLException e){
            redirectAttributes.addFlashAttribute("errors", List.of(e.getMessage()));
        }

        return "redirect:/registration?success";
    }
}
