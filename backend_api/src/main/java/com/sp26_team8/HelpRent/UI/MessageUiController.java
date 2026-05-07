package com.sp26_team8.HelpRent.UI;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sp26_team8.HelpRent.entity.Ticket;
import com.sp26_team8.HelpRent.entity.User;
import com.sp26_team8.HelpRent.service.MessageService;
import com.sp26_team8.HelpRent.service.TicketService;
import com.sp26_team8.HelpRent.service.UserService;

@Controller
public class MessageUiController {

    private final MessageService messageService;
    private final TicketService ticketService;
    private final UserService userService;

    public MessageUiController(MessageService messageService,
                               TicketService ticketService,
                               UserService userService) {
        this.messageService = messageService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    // View chat for a ticket (validateTicketView() in ticketService checks for permissions)
    //   TENANT: must be the submitter
    //   MAINTENANCE: must be staff on the ticket's property
    //   LANDLORD: must own the ticket's property
    @GetMapping("/tickets/{ticketId}/chat")
    public String chatPage(@PathVariable Long ticketId,
                           Authentication auth,
                           Model model) {
        User user = userService.getUserByEmail(auth.getName());
        Ticket ticket = ticketService.validateTicketView(ticketId, user.getUserId());

        model.addAttribute("currentUser", user);
        model.addAttribute("role", user.getRole().name());
        model.addAttribute("ticket", ticket);
        model.addAttribute("messages", messageService.getMessagesByTicket(ticketId));

        return "ticket/chat";
    }

    // Send a message (all roles)
    @PostMapping("/tickets/{ticketId}/chat")
    public String sendMessage(@PathVariable Long ticketId,
                              @RequestParam(required = false) String content,
                              @RequestParam(required = false) MultipartFile file,
                              Authentication auth) {
        User user = userService.getUserByEmail(auth.getName());

        // verify access before allowing a message
        ticketService.validateTicketView(ticketId, user.getUserId());

        messageService.sendMessage(ticketId, user.getUserId(), content, file);

        return "redirect:/tickets/" + ticketId + "/chat";
    }
}