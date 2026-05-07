package com.sp26_team8.HelpRent.UI;

import org.springframework.boot.webmvc.error.ErrorController;
//import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sp26_team8.HelpRent.entity.User;
import com.sp26_team8.HelpRent.entity.UserStatus;
import com.sp26_team8.HelpRent.entity.Unit;
import com.sp26_team8.HelpRent.service.UserService;
import com.sp26_team8.HelpRent.service.UnitService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorUiController implements ErrorController {

    private final UserService userService;
    private final UnitService unitService;

    public ErrorUiController(UserService userService, UnitService unitService) {
        this.userService = userService;
        this.unitService = unitService;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Authentication auth, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        model.addAttribute("status", status != null ? status.toString() : "500");
        model.addAttribute("message", message != null && !message.toString().isEmpty()
                ? message.toString() : "An unexpected error occurred.");

        String backLink = "";
        String backText = "";

        if (auth != null && auth.isAuthenticated()) {
            try {
                User user = userService.getUserByEmail(auth.getName());

                switch (user.getRole()) {
                    case LANDLORD -> {
                        // Landlords are always valid if they exist
                        backLink = "/dashboard";
                        backText = "Back to Dashboard";
                    }
                    case MAINTENANCE -> {
                        // Only link to dashboard if they belong to a property
                        // (propertyService would throw if not, always checks at least once)
                        backLink = "/dashboard";
                        backText = "Back to Dashboard";
                    }
                    case TENANT -> {
                        // Only link to dashboard if they have a unit
                        Unit unit = unitService.getUnitByTenant(user.getUserId());
                        if (unit != null) {
                            backLink = "/dashboard";
                            backText = "Back to Dashboard";
                        }
                        else {
                            //tenants without a unit is not a valid user!
                            user.setStatus(UserStatus.INACTIVE);
                            userService.updateUser((long)2, user);  //"admin" user is id:2, manually set to inactive if invalid user

                            backLink = "/login";
                            backText = "Back to Login";
                        }
                    }
                    default -> {
                        //no user somehow
                        backLink = "/login";
                        backText = "Back to Login";
                    }
                }
            } catch (Exception e) {
                //anything weird fails = stay with login link
                backLink = "/login";
                backText = "Back to Login";
            }
        }

        model.addAttribute("backLink", backLink);
        model.addAttribute("backText", backText);

        return "error";
    }
}