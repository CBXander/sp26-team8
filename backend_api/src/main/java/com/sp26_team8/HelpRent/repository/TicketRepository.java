package com.sp26_team8.HelpRent.repository;
import java.util.List;
import java.time.LocalDateTime;

import com.sp26_team8.HelpRent.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
    List<Ticket> findByTitleContainingIgnoreCase(String title);
    List<Ticket> findByCategoryContainingIgnoreCase(String category);
    
    List<Ticket> findByUnit(Unit unit);
    List<Ticket> findByFixture(Fixture fixture);

    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByPriority(TicketPriority priority);
    
    List<Ticket> findBySubmittedBy(User submittedBy);
    List<Ticket> findByAssignedTo(User assignedTo);
    
    List<Ticket> findByCreatedAt(LocalDateTime createdAt);   
    List<Ticket> findByUpdatedAt(LocalDateTime upatedAt);
}