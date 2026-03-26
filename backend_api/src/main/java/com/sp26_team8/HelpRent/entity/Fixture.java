package com.sp26_team8.HelpRent.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

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
    @JsonManagedReference("fixture-helpGuides")
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