package com.steve.paymybuddy.web.controller;

import com.steve.paymybuddy.dto.BankAccountDto;
import com.steve.paymybuddy.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class ExternalTransferController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/extransfer")
    public String extransferPage(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("bankAccount", new BankAccountDto());
        model.addAttribute("listBankAccount", bankAccountService.findBankAccountByUser(userDetails.getUsername()));
        return "extransfer";
    }

    @PostMapping("/extransfer/addBankAccount")
    public String addBankAccount(@ModelAttribute BankAccountDto bankAccountDto, @AuthenticationPrincipal UserDetails userDetails){
        bankAccountService.addBankAccount(userDetails.getUsername(), bankAccountDto);
        return "redirect:/user/extransfer";
    }

    @PostMapping("/extransfer/deleteBankAccount")
    public String deteleBankAccount(@RequestParam String iban){
        bankAccountService.deleteBankAccount(iban);
        return "redirect:/user/extransfer";
    }

}
