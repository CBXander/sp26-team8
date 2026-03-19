package com.sp26_team8.HelpRent.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "units")
public class Unit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;

    //NEED TO ASK ABOUT THE JPA TAGS
    @Column(nullable = false)
    private Property property;

    @Column
    private User tenant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitStatus status;

    private String unitNum;
    
    //NEED TO ASK ABOUT THE JPA TAGS
    //ASK ABOUT MAKING THIS A SUBTABLE OR SOMETHING LIKE THAT
    private List<Fixture> fixtures = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();

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

    //setters 
    public void setProperty(Property property){
        this.property = property;
    }

    public void setTenant(User tenant){
        this.tenant = tenant;
    }

    public void setUnitStatus(UnitStatus status){
        this.status = status;
    }

    public void setUnitNum(String unitNum){
        this.unitNum = unitNum;
    }

    public void setFixtures(List<Fixture> fixtures) {
        this.fixtures = fixtures;
    }
    
    public void setTickets(List<Ticket> tickets){
        this.tickets = tickets;
    }

    //getters
    public Long getUnitId(){
        return unitId;
    }

    public Property getProperty(){
        return this.property;
    }

    public User getTenant(){
        return this.tenant;
    }

    public UnitStatus getUnitStatus(){
        return this.status;
    }

    public String getUnitNum(){
        return this.unitNum;
    }

    public List<Fixture> getFixtures(){
        return this.fixtures;
    }

    public List<Ticket> getTickets(){
        return this.tickets;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt(){
        return this.updatedAt;
    }

}