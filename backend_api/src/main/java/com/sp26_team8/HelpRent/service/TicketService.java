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
    private final UserService userService;
    private final UnitService unitService;
    private final FixtureService fixtureService;
    private final PropertyService propertyService;

    public TicketService(TicketRepository ticketRepository, UserService userService, UnitService unitService, 
                        FixtureService fixtureService, PropertyService propertyService){
        this.ticketRepository = ticketRepository;
        this.userService = userService;
        this.unitService = unitService;
        this.fixtureService = fixtureService;
        this.propertyService = propertyService;
    }

    //ASSUMES THAT USERROLE HAS ALREADY BEEN VERIFIED FOR USAGE
    private Ticket validateTicketUsage(Long ticketId, Long userId){
        Ticket ticket = getTicketById(ticketId);
        User user = userService.getUserById(userId);
        
        switch (user.getRole()) {
            case LANDLORD:
                if(!ticket.getUnit().getProperty().getLandlord().getUserId().equals(userId)){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ticket belongs to a unit that does not belong to you.");
                }
                break;
            case MAINTENANCE:
                if (ticket.getAssignedTo() == null || !ticket.getAssignedTo().getUserId().equals(userId)){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ticket not assigned to you.");
                }
                break;
            case TENANT:
                if(!ticket.getSubmittedBy().getUserId().equals(userId)){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ticket belongs to a different tenant.");
                }
                break;
            default:
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "No user role found.");
        }
        return ticket;
    }
//------------------------------------- POST METHODS -------------------------------------//
    //default property create ###FOR LANDLORDS### ###FOR TENANTS###
    public Ticket createTicket(Ticket ticket, Long unitId, Long fixtureId, Long userId){
        User user = userService.validateUserRole(userId, UserRole.LANDLORD, UserRole.TENANT);
        Unit unit;   
        if (user.getRole().equals(UserRole.LANDLORD)){
            unit = unitService.verifyLandlordUnitOwnership(userId,unitId);
        }else if (user.getRole().equals(UserRole.TENANT)){
            unit = unitService.getUnitByTenant(userId);
            
        } else {
            unit = unitService.getUnitById(unitId); //will never happen since validateUserRole() will throw the error before it gets here
        }

        if(fixtureId != null){
            ticket.setFixture(fixtureService.getFixtureById(fixtureId));
        }

        ticket.setSubmittedBy(user);
        ticket.setUnit(unit);
        ticket.setStatus(TicketStatus.OPEN);

        return ticketRepository.save(ticket);
    }

//------------------------------------- PUT METHODS -------------------------------------//
    //default UPDATE ###FOR LANDLORDS### ###FOR TENANTS###
    public Ticket updateTicket(Long ticketId, Ticket updatedTicket, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD, UserRole.TENANT);
        Ticket ticket = validateTicketUsage(ticketId, userId);

        
        ticket.setTitle(updatedTicket.getTitle());
        ticket.setDescription(updatedTicket.getDescription());
        ticket.setCategory(updatedTicket.getCategory());

        return ticketRepository.save(ticket);
    }

    //assign ticket to staff member ###FOR LANDLORDS###
    public Ticket assignTicket(Long ticketId, Long staffId, Long userId){
        //verify user is landlord
        userService.validateUserRole(userId, UserRole.LANDLORD);
        User staff = userService.validateUserRole(staffId,UserRole.MAINTENANCE);
        //verify user is correct landlord
        Ticket ticket = validateTicketUsage(ticketId, userId);
        //verify that staff is in property's staff list
        if(!ticket.getUnit().getProperty().getStaff().contains(staff)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff member is not part of property's staff list.");
        }

        ticket.setAssignedTo(staff);
        ticket.setStatus(TicketStatus.OPEN);
        return ticketRepository.save(ticket);
    }

    //set ticket priority ###FOR LANDLORDS### ###FOR MAINTENANCE###
    public Ticket setTicketPriority(Long ticketId, TicketPriority priority, Long userId){
        User user = userService.validateUserRole(userId, UserRole.TENANT,UserRole.LANDLORD, UserRole.MAINTENANCE);
        Ticket ticket = validateTicketUsage(ticketId, userId);
        if(ticket.getStatus().equals(TicketStatus.CANCELLED) || ticket.getStatus().equals(TicketStatus.CLOSED)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket priority cannot be modified once closed or cancelled.");
        }
        if (!ticket.getStatus().equals(TicketStatus.OPEN) && user.getRole().equals(UserRole.TENANT)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket priority cannot be modified by tenant once processed.");
        }

        ticket.setPriority(priority);

        return ticketRepository.save(ticket);
    }

    //set ticket status ###FOR LANDLORDS### ###FOR MAINTENANCE###
    public Ticket setTicketStatus(Long ticketId, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD, UserRole.MAINTENANCE);
        Ticket ticket = validateTicketUsage(ticketId, userId);
        
        switch (ticket.getStatus()) {
            case OPEN:
                ticket.setStatus(TicketStatus.IN_PROGRESS);
                break;
        
            case IN_PROGRESS:
                ticket.setStatus(TicketStatus.COMPLETED);
                break;
            case COMPLETED:
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ticket awaiting tenant confirmation.");
            case CLOSED:
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ticket already closed.");

            case CANCELLED:
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ticket canceled.");

            default:
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "No ticket status found.");
        }
        return ticketRepository.save(ticket);
    }
    
    //complete ticket  ###FOR LANDLORDS### ###FOR TENANTS###
    public Ticket completeTicket(Long ticketId, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD, UserRole.TENANT);
        Ticket ticket = validateTicketUsage(ticketId, userId);

        if (ticket.getStatus() != TicketStatus.COMPLETED){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket is not marked completed.");
        }
        ticket.setStatus(TicketStatus.CLOSED);
        return ticketRepository.save(ticket);
    }
    //cancel ticket ###FOR LANDLORDS### ###FOR TENANTS###
    public Ticket cancelTicket(Long ticketId, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD, UserRole.TENANT);
        Ticket ticket = validateTicketUsage(ticketId, userId);
        if(ticket.getStatus() == TicketStatus.CLOSED){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot cancel a closed ticket.");
        }
        if(ticket.getStatus() == TicketStatus.CANCELLED){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ticket is already cancelled.");
        }
        ticket.setStatus(TicketStatus.CANCELLED);
        return ticketRepository.save(ticket);
    }

//------------------------------------- GET METHODS -------------------------------------//    
    //default get ticket by id
    public Ticket getTicketById(Long id){
        return ticketRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found."));
    }

    //default get all tickets
    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    //tenant view of tickets ###FOR TENANTS###
    public List<Ticket> getTicketByTenant(Long userId){
        User user = userService.validateUserRole(userId, UserRole.TENANT);
        return ticketRepository.findBySubmittedBy(user);
    } 

    //maintenance view of tickets ###FOR MAINTENANCE###
    public List<Ticket> getTicketByStaff(Long userId){
        User user = userService.validateUserRole(userId, UserRole.MAINTENANCE);
        return ticketRepository.findByAssignedTo(user);
    }

    //landlord viwe of tickets ###FOR LANDLORD###
    public List<Ticket> getTicketByProperty(Long propertyId, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);
        Property property = propertyService.verifyLandlordOwnership(propertyId, userId);
        return ticketRepository.findByUnit_Property(property);
    }

    //maintenance statistics view  ###FOR LANDLORD### ###FOR MAINTENANCE### ###FOR TENANTS###
    public List<Ticket> getTicketsByUnit(Long unitId, Long userId){
        User user = userService.validateUserRole(userId, UserRole.LANDLORD, UserRole.MAINTENANCE, UserRole.TENANT);
        Unit unit = unitService.getUnitById(unitId);
        
        switch(user.getRole()){
            case LANDLORD:
                if(!unit.getProperty().getLandlord().getUserId().equals(userId)){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unit does not belong to you.");
                }
                break;
            case MAINTENANCE:
                if(!unit.getProperty().getStaff().contains(user)){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not assigned to this property.");
                }
                break;
            case TENANT:
                if(unit.getTenant() == null || !unit.getTenant().getUserId().equals(userId)){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This is not your unit.");
                }
                break;
            }
        return ticketRepository.findByUnit(unit);
    }
    //maintenance statistics: view by fixture (shared between properties if different properties have same fixture, it is fine to see that this fixture has issue x)
    public List<Ticket> getTicketsByFixture(Long fixtureId){
        Fixture fixture = fixtureService.getFixtureById(fixtureId);
        return ticketRepository.findByFixture(fixture);
    }
    
//------------------------------------- DELETE METHODS -------------------------------------//
    //DELETE
    //deleting a closed ticket reduces usefullness of maintenance history!
    public void deleteTicket(Long ticketId, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);
        Ticket ticket = validateTicketUsage(ticketId, userId);
        
        ticketRepository.delete(ticket);
    }
}
