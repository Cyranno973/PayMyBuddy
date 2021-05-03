package com.steve.paymybuddy.web.controller;

import com.steve.paymybuddy.dto.InternalTransferDto;
import com.steve.paymybuddy.service.TransferService;
import com.steve.paymybuddy.service.UserService;
import com.steve.paymybuddy.web.exception.DataNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class InternalTransferController {

    private final TransferService transferService;
    private final UserService userService;

    public InternalTransferController(TransferService transferService, UserService userService) {
        this.transferService = transferService;
        this.userService = userService;
    }


    @GetMapping("/transfer")
    public String transferPage(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("internalTransfer", new InternalTransferDto());
        model.addAttribute("relations", userService.listEmailRelation(userDetails.getUsername()));
        model.addAttribute("transfers", transferService.findInternalTransferByUser(userDetails.getUsername()));
        return "transfer2";
    }

    @PostMapping("/transfer/internal")
    public String internalTransfer(@ModelAttribute InternalTransferDto internalTransferDto, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) throws SQLException {
        internalTransferDto.setEmailSender(userDetails.getUsername());
        try{
            transferService.doInternalTransfer(internalTransferDto);
        } catch (DataNotExistException | SQLException e){
            redirectAttributes.addFlashAttribute("errors", List.of(e.getMessage()));
        }
        return "redirect:/user/transfer";
    }
}

