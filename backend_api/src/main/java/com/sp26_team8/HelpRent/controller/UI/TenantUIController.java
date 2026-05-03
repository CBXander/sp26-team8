package com.sp26_team8.HelpRent.controller.UI;

import com.sp26_team8.HelpRent.entity.Ticket;
import com.sp26_team8.HelpRent.entity.TicketPriority;
import com.sp26_team8.HelpRent.entity.User;
import com.sp26_team8.HelpRent.service.TicketService;
import com.sp26_team8.HelpRent.service.UnitService;
import com.sp26_team8.HelpRent.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sp26_team8.HelpRent.service.MessageService;
import org.springframework.web.multipart.MultipartFile;
@Controller
public class TenantUIController {

    private final TicketService ticketService;
    private final UserService userService;
    private final UnitService unitService;
    private final MessageService messageService;

    public TenantUIController(TicketService ticketService,
                          UserService userService,
                          UnitService unitService,
                          MessageService messageService) {
    this.ticketService = ticketService;
    this.userService = userService;
    this.unitService = unitService;
    this.messageService = messageService;
    }
    @GetMapping("/tenant/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        model.addAttribute("tenant", user);
        model.addAttribute("tickets", ticketService.getTicketByTenant(user.getUserId()));
        model.addAttribute("tenantUnit", unitService.getUnitByTenant(user.getUserId()));

        return "tenant/dashboard";
    }

    @GetMapping("/tenant/request")
    public String requestPage(Authentication authentication,
                              Model model,
                              @RequestParam(required = false) String cancelled) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        model.addAttribute("tickets", ticketService.getTicketByTenant(user.getUserId()));
        model.addAttribute("tenant", user);

        if (cancelled != null) {
            model.addAttribute("cancelled", true);
        }

        return "tenant/request";
    }

    @GetMapping("/tenant/chat")
    public String chatPage(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        model.addAttribute("tenant", user);
        return "tenant/chat";
    }

    @GetMapping("/tenant/guides")
    public String guidesPage(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        model.addAttribute("tenant", user);
        return "tenant/guides";
    }

    @PostMapping("/tenant/request")
    public String submitRequest(Authentication authentication,
                                @RequestParam String title,
                                @RequestParam String category,
                                @RequestParam String priority,
                                @RequestParam String description) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        Ticket ticket = new Ticket();
        ticket.setTitle(title);
        ticket.setCategory(category);
        ticket.setDescription(description);
        ticket.setPriority(TicketPriority.valueOf(priority));

        ticketService.createTicket(ticket, null, null, user.getUserId());

        return "redirect:/tenant/request";
    }

    @PostMapping("/tenant/request/{ticketId}/cancel")
    public String cancelRequest(Authentication authentication,
                                @PathVariable Long ticketId,
                                RedirectAttributes redirectAttributes) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        ticketService.cancelTicket(ticketId, user.getUserId());

        redirectAttributes.addFlashAttribute("cancelled", true);
        return "redirect:/tenant/request";
    }


    // Chat related
    @GetMapping("/tenant/chat/{ticketId}")
    public String chatPage(@PathVariable Long ticketId,
                        Authentication authentication,
                        Model model) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        Ticket ticket = ticketService.getTicketById(ticketId);

        // verify this ticket belongs to the logged-in tenant
        if (!ticket.getSubmittedBy().getUserId().equals(user.getUserId())) {
            return "redirect:/tenant/dashboard";
        }

        model.addAttribute("tenant", user);
        model.addAttribute("ticket", ticket);
        model.addAttribute("messages", messageService.getMessagesByTicket(ticketId));

        return "tenant/chat";
    }

    @PostMapping("/tenant/chat/{ticketId}")
    public String sendMessage(@PathVariable Long ticketId,
                            @RequestParam(required = false) String content,
                            @RequestParam(required = false) MultipartFile file,
                            Authentication authentication) {
    String email = authentication.getName();
    User user = userService.getUserByEmail(email);

    Ticket ticket = ticketService.getTicketById(ticketId);

    if (!ticket.getSubmittedBy().getUserId().equals(user.getUserId())) {
        return "redirect:/tenant/dashboard";
    }

    messageService.sendMessage(ticketId, user.getUserId(), content, file);

    return "redirect:/tenant/chat/" + ticketId;
}
}