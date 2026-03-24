package com.sp26_team8.HelpRent.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fixtures")
public class Fixture{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fixtureId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "fixture")
    private List<HelpGuide> helpGuides = new ArrayList<>();

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

    //getters
    public Long getFixtureId(){
        return this.fixtureId;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public List<HelpGuide> getGuides(){
        return this.helpGuides;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    } 

    public LocalDateTime getUpdatedAt(){
        return this.updatedAt;
    }

    //setters
    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setHelpGuides(List<HelpGuide> guides){
        this.helpGuides = guides;
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Fixture fixture = (Fixture) o;
        return this.fixtureId != null && this.fixtureId.equals(fixture.getFixtureId());
    }

    @Override
    public int hashCode(){
        return this.fixtureId != null ? this.fixtureId.hashCode() : 0;
    }
}