package com.sp26_team8.HelpRent.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sp26_team8.HelpRent.entity.Ticket;
import com.sp26_team8.HelpRent.entity.TicketPriority;
import com.sp26_team8.HelpRent.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket, @RequestParam Long unitId, @RequestParam(required = false) Long fixtureId, @RequestParam Long userId) {
        return new ResponseEntity<>(ticketService.createTicket(ticket, unitId, fixtureId, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long ticketId, @RequestBody Ticket updatedTicket, @RequestParam Long userId) {
        return ResponseEntity.ok(ticketService.updateTicket(ticketId, updatedTicket, userId));
    }

    @PutMapping("/{ticketId}/assign/{staffId}")
    public ResponseEntity<Ticket> assignTicket(@PathVariable Long ticketId, @PathVariable Long staffId, @RequestParam Long userId) {
        return ResponseEntity.ok(ticketService.assignTicket(ticketId, staffId, userId));
    }

    @PutMapping("/{ticketId}/priority")
    public ResponseEntity<Ticket> setTicketPriority(@PathVariable Long ticketId, @RequestParam TicketPriority priority, @RequestParam Long userId) {
        return ResponseEntity.ok(ticketService.setTicketPriority(ticketId, priority, userId));
    }

    @PutMapping("/{ticketId}/status")
    public ResponseEntity<Ticket> setTicketStatus(@PathVariable Long ticketId, @RequestParam Long userId) {
        return ResponseEntity.ok(ticketService.setTicketStatus(ticketId, userId));
    }
    
    @PutMapping("/{ticketId}/complete")
    public ResponseEntity<Ticket> completeTicket(@PathVariable Long ticketId, @RequestParam Long userId) {
        return ResponseEntity.ok(ticketService.completeTicket(ticketId, userId));
    }

    @PutMapping("/{ticketId}/cancel")
    public ResponseEntity<Ticket> cancelTicket(@PathVariable Long ticketId, @RequestParam Long userId) {
        return ResponseEntity.ok(ticketService.cancelTicket(ticketId, userId));
    }

    
    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long ticketId) {
        return ResponseEntity.ok(ticketService.getTicketById(ticketId));
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }
    
    @GetMapping("/tenant")
    public ResponseEntity<List<Ticket>> getTicketsByTenant(@RequestParam Long userId) {
        return ResponseEntity.ok(ticketService.getTicketByTenant(userId));
    }
    
    @GetMapping("/staff")
    public ResponseEntity<List<Ticket>> getTicketsByStaff(@RequestParam Long userId) {
        return ResponseEntity.ok(ticketService.getTicketByStaff(userId));
    }
    
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Ticket>> getTicketsByProperty(@PathVariable Long propertyId, @RequestParam Long userId) {
        return ResponseEntity.ok(ticketService.getTicketByProperty(propertyId, userId));
    }
    
    @GetMapping("/unit/{unitId}")
    public ResponseEntity<List<Ticket>> getTicketsByUnit(@PathVariable Long unitId, @RequestParam Long userId) {
        return ResponseEntity.ok(ticketService.getTicketsByUnit(unitId, userId));
    }
    
    @GetMapping("/fixture/{fixtureId}")
    public ResponseEntity<List<Ticket>> getTicketsByFixture(@PathVariable Long fixtureId) {
        return ResponseEntity.ok(ticketService.getTicketsByFixture(fixtureId));
    }
    
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId, @RequestParam Long userId) {
        ticketService.deleteTicket(ticketId, userId);
        return ResponseEntity.noContent().build();
    }
}
