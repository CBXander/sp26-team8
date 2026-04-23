package com.sp26_team8.HelpRent.UI;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sp26_team8.HelpRent.service.*;
import com.sp26_team8.HelpRent.entity.*;

@Controller
@RequestMapping("/ui/tickets")
public class TicketUiController {

    private final TicketService ticketService;
    private final UserService userService;
    private final UnitService unitService;
    private final PropertyService propertyService;

    public TicketUiController(TicketService ticketService, UserService userService, UnitService unitService, PropertyService propertyService){
        this.ticketService = ticketService;
        this.userService = userService;
        this.unitService = unitService;
        this.propertyService = propertyService;
    }

    //========= View Ticket =========//
    @GetMapping("/{ticketId}")
    public String viewTicket(Authentication auth, @PathVariable Long ticketId, Model model){
        User user = userService.getUserByEmail(auth.getName());
        model.addAttribute("role", user.getRole().name());

        Ticket ticket = ticketService.validateTicketView(ticketId, user.getUserId());
        
        model.addAttribute("ticket", ticket);

        if (user.getRole() == UserRole.LANDLORD) {
            model.addAttribute("staff", ticket.getUnit().getProperty().getStaff());
        }

        return "ticket/viewTicket";
    }
    

    //========= Manage Ticket/Create Ticket =========//

    @GetMapping("/new")
    public String newTicketForm(Authentication auth, @RequestParam Long unitId, Model model) {
        User user = userService.getUserByEmail(auth.getName());
        Unit unit = unitService.getUnitById(unitId);
        
        model.addAttribute("role", user.getRole().name());
        model.addAttribute("unit", unit);
        
        return "ticket/newTicketForm";
    }

    @PostMapping("/new")
    public String createTicket(Authentication auth, @RequestParam Long unitId,
                                @RequestParam(required = false) Long fixtureId, @RequestParam String title, 
                                @RequestParam String description, @RequestParam(required = false) String category) {
        User user = userService.getUserByEmail(auth.getName());

        Ticket ticket = new Ticket();
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setCategory(category);

        Ticket created = ticketService.createTicket(ticket, unitId, fixtureId, user.getUserId());
        
        return "redirect:/ui/tickets/" + created.getTicketId();
    }

    @PostMapping("/{ticketId}/assign")
    public String assignTicket(Authentication auth, @PathVariable Long ticketId, @RequestParam Long staffId) {
        User user = userService.getUserByEmail(auth.getName());
        ticketService.assignTicket(ticketId, staffId, user.getUserId());
        return "redirect:/ui/tickets/" + ticketId;
    }

    @PostMapping("/{ticketId}/priority")
    public String setPriority(Authentication auth, @PathVariable Long ticketId, @RequestParam TicketPriority priority) {
        User user = userService.getUserByEmail(auth.getName());
        ticketService.setTicketPriority(ticketId, priority, user.getUserId());
        return "redirect:/ui/tickets/" + ticketId;
    }

    @PostMapping("/{ticketId}/status")
    public String advanceStatus(Authentication auth, @PathVariable Long ticketId) {
        User user = userService.getUserByEmail(auth.getName());
        ticketService.setTicketStatus(ticketId, user.getUserId());
        return "redirect:/ui/tickets/" + ticketId;
    }

    @PostMapping("/{ticketId}/complete")
    public String completeTicket(Authentication auth, @PathVariable Long ticketId) {
        User user = userService.getUserByEmail(auth.getName());
        ticketService.completeTicket(ticketId, user.getUserId());
        return "redirect:/ui/tickets/" + ticketId;
    }

    @PostMapping("/{ticketId}/cancel")
    public String cancelTicket(Authentication auth, @PathVariable Long ticketId) {
        User user = userService.getUserByEmail(auth.getName());
        ticketService.cancelTicket(ticketId, user.getUserId());
        return "redirect:/ui/tickets/" + ticketId;
    }
}