package com.steve.paymybuddy.web.controller;

import com.steve.paymybuddy.dto.BankAccountDto;
import com.steve.paymybuddy.dto.ExternalTransferDto;
import com.steve.paymybuddy.model.BankAccount;
import com.steve.paymybuddy.service.BankAccountService;
import com.steve.paymybuddy.service.TransferService;
import com.steve.paymybuddy.web.exception.DataAlreadyExistException;
import com.steve.paymybuddy.web.exception.DataMissingException;
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
public class ExternalTransferController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransferService transferService;

    @GetMapping("/extransfer")
    public String extransferPage(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("externalTransfers", transferService.findExternalTransferByUser(userDetails.getUsername()));
        ExternalTransferDto dto = new ExternalTransferDto();
        model.addAttribute("externalTransfer",dto);
        model.addAttribute("bankAccount", new BankAccountDto());
        List<BankAccount> accounts = bankAccountService.findBankAccountByUser(userDetails.getUsername());
        model.addAttribute("listBankAccount", accounts);
        if (!accounts.isEmpty()) {
            dto.setIbanUser(accounts.get(accounts.size()-1).getIban());
        }
        return "extransfer";
    }

    @PostMapping("/extransfer/addBankAccount")
    public String addBankAccount(@ModelAttribute BankAccountDto bankAccountDto, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes){
        try {
            bankAccountService.addBankAccount(userDetails.getUsername(), bankAccountDto);
        } catch (DataAlreadyExistException | DataMissingException | SQLException e){
            redirectAttributes.addFlashAttribute("errors", List.of(e.getMessage()));
        }
        return "redirect:/user/extransfer";
    }

    @PostMapping("/extransfer/deleteBankAccount")
    public String deteleBankAccount(@RequestParam String iban){
        bankAccountService.deleteBankAccount(iban);
        return "redirect:/user/extransfer";
    }

    @PostMapping("/extransfer/external")
    public String doExternalTransfer(@ModelAttribute ExternalTransferDto externalTransferDto, @AuthenticationPrincipal UserDetails userDetails) throws SQLException {
        externalTransferDto.setEmailUser(userDetails.getUsername());
        transferService.doExternalTransfer(externalTransferDto);
        return "redirect:/user/extransfer";
    }

}
