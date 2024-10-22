package com.chaunhat.fluffyfunicular.controller.account;

import com.chaunhat.fluffyfunicular.model.Account;
import com.chaunhat.fluffyfunicular.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/profile/{email}")
    public String viewProfile(@PathVariable String email, Model model) throws SQLException {
        Account account = accountService.getAccountByEmail(email);
        if (account != null) {
            model.addAttribute("account", account);
            return null;
        } else {
            model.addAttribute("error", "Account not found");
            return "error";
        }
    }
}
