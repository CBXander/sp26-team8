package com.sp26_team8.HelpRent.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "message_attachments")
public class MessageAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachmentId;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileType;

    public Long getAttachmentId() {
        return attachmentId;
    }

    public Message getMessage() {
        return message;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}