package com.steve.paymybuddy.web.controller;

import com.steve.paymybuddy.service.UserService;
import com.steve.paymybuddy.web.exception.DataAlreadyExistException;
import com.steve.paymybuddy.web.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class RelationController {

    @Autowired
    private UserService userService;

    @GetMapping("/relation")
    public String relationPage(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("relations", userService.listEmailRelation(userDetails.getUsername()));
        return "relation";
    }

    @PostMapping("/relation/addBuddy")
    public String addRelation(@RequestParam String emailBuddy, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes){
        try {
            userService.addBuddy(userDetails.getUsername(), emailBuddy);
        } catch (DataAlreadyExistException | DataNotFoundException daee){
            redirectAttributes.addFlashAttribute("errors", List.of(daee.getMessage()));
        }
        return "redirect:/user/relation";
    }

    @PostMapping("/relation")
    public String deleteRelation(@RequestParam Integer id){
        userService.deleteRelation(id);
        return "redirect:/user/relation";
    }


}
