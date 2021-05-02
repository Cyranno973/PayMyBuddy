package com.steve.paymybuddy.web.controller;

import com.steve.paymybuddy.dto.ExternalTransferDto;
import com.steve.paymybuddy.dto.InternalTransferDto;
import com.steve.paymybuddy.dto.TransferDto;
import com.steve.paymybuddy.service.TransferService;
import com.steve.paymybuddy.service.UserService;
import com.steve.paymybuddy.web.exception.DataNotExistException;
import com.steve.paymybuddy.web.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
public class InternalTransferController {

    @Autowired
    private TransferService transferService;
    @Autowired
    private UserService userService;


    @GetMapping("/transfer")
    public String transferPage(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("internalTransfer", new InternalTransferDto());
        model.addAttribute("relations", userService.listEmailRelation(userDetails.getUsername()));
        model.addAttribute("transfers", transferService.findInternalTransferByUser(userDetails.getUsername()));
        return "transfer2";
    }

    @PostMapping("/transfer/internal")
    public String internalTransfer(@ModelAttribute InternalTransferDto internalTransferDto, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes){
        internalTransferDto.setEmailSender(userDetails.getUsername());
        try{
            transferService.doInternalTransfer(internalTransferDto);
        } catch (DataNotExistException e){
            redirectAttributes.addFlashAttribute("errors", List.of(e.getMessage()));
        }
        return "redirect:/user/transfer";
    }
}

