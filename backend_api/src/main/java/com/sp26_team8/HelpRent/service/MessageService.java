package com.sp26_team8.HelpRent.service;

import com.sp26_team8.HelpRent.entity.Message;
import com.sp26_team8.HelpRent.entity.Ticket;
import com.sp26_team8.HelpRent.entity.User;
import com.sp26_team8.HelpRent.repository.MessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// Imports for attachment
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.sp26_team8.HelpRent.entity.MessageAttachment;
import com.sp26_team8.HelpRent.repository.MessageAttachmentRepository;
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final TicketService ticketService;
    private final UserService userService;

    public MessageService(MessageRepository messageRepository,
                      MessageAttachmentRepository messageAttachmentRepository,
                      TicketService ticketService,
                      UserService userService) {
    this.messageRepository = messageRepository;
    this.messageAttachmentRepository = messageAttachmentRepository;
    this.ticketService = ticketService;
    this.userService = userService;
}

    public List<Message> getMessagesByTicket(Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        return messageRepository.findByTicketOrderBySentAtAsc(ticket);
    }

    public Message sendMessage(Long ticketId,
                            Long senderUserId,
                            String content,
                            MultipartFile file) {
    boolean hasText = content != null && !content.trim().isEmpty();
    boolean hasFile = file != null && !file.isEmpty();

    if (!hasText && !hasFile) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message cannot be empty.");
    }

    Ticket ticket = ticketService.getTicketById(ticketId);
    User sender = userService.getUserById(senderUserId);

    Message message = new Message();
    message.setTicket(ticket);
    message.setSender(sender);
    message.setContent(hasText ? content.trim() : "");

    Message savedMessage = messageRepository.save(message);

    if (hasFile) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String uniqueFilename = UUID.randomUUID() + extension;
            Path uploadDir = Paths.get("uploads");
            Files.createDirectories(uploadDir);

            Path uploadPath = uploadDir.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

            MessageAttachment attachment = new MessageAttachment();
            attachment.setMessage(savedMessage);
            attachment.setFilePath("/uploads/" + uniqueFilename);

            String contentType = file.getContentType();
            if (contentType == null || contentType.isBlank()) {
                originalFilename = file.getOriginalFilename();

                if (originalFilename != null) {
                    String lower = originalFilename.toLowerCase();

                    if (lower.endsWith(".png")) {
                        contentType = "image/png";
                    } else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) {
                        contentType = "image/jpeg";
                    } else if (lower.endsWith(".webp")) {
                        contentType = "image/webp";
                    } else if (lower.endsWith(".gif")) {
                        contentType = "image/gif";
                    } else if (lower.endsWith(".mp4")) {
                        contentType = "video/mp4";
                    } else if (lower.endsWith(".webm")) {
                        contentType = "video/webm";
                    } else if (lower.endsWith(".mov")) {
                        contentType = "video/quicktime";
                    } else {
                        contentType = "application/octet-stream";
                    }
                } else {
                    contentType = "application/octet-stream";
                }
            }

attachment.setFileType(contentType);
            messageAttachmentRepository.save(attachment);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload file.");
        }
    }

    return savedMessage;
}    
    // Attachment handling methods

    private final MessageAttachmentRepository messageAttachmentRepository;

}