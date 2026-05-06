package com.sp26_team8.HelpRent.UI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginUiController {
    @GetMapping("/ui/login")
    public String loginPage() {
        return "login";
    }

}
