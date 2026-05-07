package com.sp26_team8.HelpRent.UI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.sp26_team8.HelpRent.entity.Property;
import com.sp26_team8.HelpRent.entity.Ticket;
import com.sp26_team8.HelpRent.entity.User;
import com.sp26_team8.HelpRent.entity.UserRole;
import com.sp26_team8.HelpRent.entity.UserStatus;
import com.sp26_team8.HelpRent.service.PropertyService;
import com.sp26_team8.HelpRent.service.TicketService;
import com.sp26_team8.HelpRent.service.UnitService;
import com.sp26_team8.HelpRent.service.UserService;
;


@Controller
@RequestMapping("")
public class UserUiController {
    private final UserService userService;
    private final PropertyService propertyService;
    private final TicketService ticketService;
    private final UnitService unitService;
    public UserUiController(UserService userService, PropertyService propertyService, TicketService ticketService, UnitService unitService){
        this.userService = userService;
        this.propertyService = propertyService;
        this.ticketService = ticketService;
        this.unitService = unitService;
    }

    //======== Login Methods ===========//
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    //======== Signup Methods ===========//
    @GetMapping("/signup")
    public String signupPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String email, @RequestParam String password, @RequestParam String firstName,
                        @RequestParam String lastName, Model model)
    {
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(UserRole.LANDLORD);
        user.setStatus(UserStatus.ACTIVE);

        try {
            userService.createUser(user);
            return "redirect:/login";
        } catch (ResponseStatusException e){
            model.addAttribute("error", "An account with this email already exists.");
            return "signup";
        }

    }

    //======== Dashboard ========//
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model){
        User user = userService.getUserByEmail(auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("role", user.getRole().name());

        
        switch (user.getRole()) {
            case LANDLORD -> {
                Property property = propertyService.getPropertyByLandlord(user.getUserId());
                if (property != null) {
                    List<Ticket> unassignedTickets = ticketService.getUnassignedTickets(property.getPropertyId(),user.getUserId());
                    Map<String, Integer> staffTicketCounts = new HashMap<>();
                    for (User staff : property.getStaff()) {
                        List<Ticket> staffTickets = ticketService.getTicketByStaff(staff.getUserId());
                        staffTicketCounts.put(staff.getUserId().toString(), staffTickets.size());
                    }

                    model.addAttribute("property", property);
                    model.addAttribute("staffTicketCounts", staffTicketCounts);
                    model.addAttribute("unassigned", unassignedTickets);
                }
            }

            case MAINTENANCE -> {
                List<Ticket> tickets = ticketService.getTicketByStaff(user.getUserId());
                for (Ticket ticket : tickets) {
                    ticket.getUnit().getUnitAddress();
                }
                model.addAttribute("tickets", tickets);
            }

            case TENANT -> {
                model.addAttribute("tickets", ticketService.getTicketByTenant(user.getUserId()));
                model.addAttribute("tenantUnit", unitService.getUnitByTenant(user.getUserId()));
            }
        }

        return "dashboard";
    }
}
