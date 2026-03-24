package com.sp26_team8.HelpRent.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column
    private String category;

    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(name = "unit_id", updatable = false)
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "fixture_id", updatable = false)
    private Fixture fixture;

    @ManyToOne
    @JoinColumn(name = "submitted_by_id", updatable = false)
    private User submittedBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return this.category; }
    public void setCategory(String category) {this.category = category;}

    public TicketPriority getPriority(){ return priority; }
    public void setPriority(TicketPriority priority){ this.priority = priority; }

    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }

    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }

    public Fixture getFixture(){ return fixture; }
    public void setFixture(Fixture fixture){ this.fixture = fixture; }

    public User getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(User user) { this.submittedBy = user; }

    public User getAssignedTo(){ return assignedTo;}
    public void setAssignedTo(User user){ this.assignedTo = user;}

    public LocalDateTime getCreatedAt() { return createdAt; }
    
    public LocalDateTime getUpdatedAt(){ return this.updatedAt;}
    
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return this.ticketId != null && this.ticketId.equals(ticket.getTicketId());
    }

    @Override
    public int hashCode(){
        return this.ticketId != null ? this.ticketId.hashCode() : 0;
    }
}