package com.sp26_team8.HelpRent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sp26_team8.HelpRent.entity.MessageAttachment;
import com.sp26_team8.HelpRent.entity.Message;

@Repository
public interface MessageAttachmentRepository extends JpaRepository<MessageAttachment, Long> {
    List<MessageAttachment> findByMessage(Message message);
}