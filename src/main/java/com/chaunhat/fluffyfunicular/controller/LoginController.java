package com.chaunhat.fluffyfunicular.controller;

import com.chaunhat.fluffyfunicular.model.Account;
import com.chaunhat.fluffyfunicular.service.AccountService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ServletContext context;

    @GetMapping
    public String showLoginPage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("name") != null) {
            return "redirect:/";
        }
        return "/login";
    }

    @PostMapping
    public String handleLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              Model model, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        Account user = accountService.getAccount(email, password);
        if (user == null) {
            model.addAttribute("invalidCredential", "Invalid email or password.");
            model.addAttribute("email", email);
            return "/login";
        }

        session.setAttribute("email", user.email());
        session.setAttribute("name", user.name());
        session.setAttribute("isAdmin", user.account_role() == 1);

        return "redirect:/";
    }
}


