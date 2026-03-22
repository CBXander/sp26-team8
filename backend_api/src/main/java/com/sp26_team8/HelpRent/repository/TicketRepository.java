package com.sp26_team8.HelpRent.repository;
import java.util.List;
import java.time.LocalDateTime;

import com.sp26_team8.HelpRent.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TicketRepository extends JpaRepository<Unit, Long>{
    List<Ticket> findByCategoryContainingIgnoreCase(String title);
    List<Ticket> findByUnit(Unit unit);
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByUser(User submittedBy);
    List<Ticket> findByDateCreated(LocalDateTime createdAt);
}