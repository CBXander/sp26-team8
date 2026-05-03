package com.sp26_team8.HelpRent.controller.UI;

import com.sp26_team8.HelpRent.entity.User;
import com.sp26_team8.HelpRent.entity.UserRole;
import com.sp26_team8.HelpRent.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    private final UserService userService;

    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}