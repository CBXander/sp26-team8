package com.sp26_team8.HelpRent.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "helpguides")
public class HelpGuide{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guideId;
    
    @Column(nullable=false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String category;

    @ManyToOne
    @JoinColumn(name = "fixture_id", nullable = true)   //general helpguides will have no fixtureId as they are not associated with a fixture 
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
    public Long getGuideId(){
        return this.guideId;
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
        return this.guideId != null && this.guideId.equals(guide.getGuideId());
    }

    @Override
    public int hashCode(){
        return this.guideId != null ? this.guideId.hashCode() : 0;
    }
}