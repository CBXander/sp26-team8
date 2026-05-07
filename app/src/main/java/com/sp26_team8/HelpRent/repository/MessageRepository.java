package com.sp26_team8.HelpRent.repository;

import com.sp26_team8.HelpRent.entity.Message;
import com.sp26_team8.HelpRent.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByTicketOrderBySentAtAsc(Ticket ticket);
}