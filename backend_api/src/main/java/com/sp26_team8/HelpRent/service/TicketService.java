package com.sp26_team8.HelpRent.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.sp26_team8.HelpRent.repository.TicketRepository;
import com.sp26_team8.HelpRent.entity.*;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    public TicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    //CRUD REQUIREMENTS
    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    //CREATE
    public Ticket createTicket(Ticket ticket){
        ticket.setStatus(TicketStatus.OPEN);
        return ticketRepository.save(ticket);
    }

    //UPDATE
    public Ticket updateTicket(Long id, Ticket updatedTicket){
        return ticketRepository.findById(id).map(ticket->{
            ticket.setTitle(updatedTicket.getTitle());
            ticket.setDescription(updatedTicket.getDescription());
            ticket.setCategory(updatedTicket.getCategory());
            ticket.setStatus(updatedTicket.getStatus());
            
            return ticketRepository.save(ticket);
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found."));
    }

    //READ
    public Ticket getTicketById(Long id){
        return ticketRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found."));
    }

    //DELETE
    public void deleteTicket(Long id){
        if(!ticketRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found.");
        }
        ticketRepository.deleteById(id);
    }
}
