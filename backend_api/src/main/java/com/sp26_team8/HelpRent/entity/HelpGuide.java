package com.sp26_team8.HelpRent.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "helpguides")
public class HelpGuide{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long helpGuideId;
    
    @Column(nullable=false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String category;

    @ManyToOne
    @JoinColumn(name = "fixture_id", nullable = true)   //general helpguides will have no fixtureId as they are not associated with a fixture 
    @JsonBackReference("fixture-helpGuides")
    private Fixture fixture;


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
    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setFixture(Fixture fixture){
        this.fixture = fixture;
    }

    //getters
    public Long getHelpGuideId(){
        return this.helpGuideId;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public String getCategory(){
        return this.category;
    }

    public Fixture getFixture(){
        return this.fixture;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt(){
        return this.updatedAt;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        HelpGuide guide = (HelpGuide) o;
        return this.helpGuideId != null && this.helpGuideId.equals(guide.getHelpGuideId());
    }

    @Override
    public int hashCode(){
        return this.helpGuideId != null ? this.helpGuideId.hashCode() : 0;
    }
}