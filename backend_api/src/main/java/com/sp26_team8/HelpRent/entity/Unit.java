package com.sp26_team8.HelpRent.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "units", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"unit_address","unit_number"})
})
public class Unit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;

    @ManyToOne
    @JoinColumn(name="property_id", nullable = false)
    private Property property;

    @OneToOne
    @JoinColumn(name = "tenant_id")
    private User tenant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitStatus status;

    @Column
    private String unitAddress;
    
    @Column
    private String unitNumber;
    
    @ManyToMany
    @JoinTable(
        name = "unit_fixtures",
        joinColumns = @JoinColumn(name="unit_id"),
        inverseJoinColumns = @JoinColumn(name="fixture_id")
    )
    private List<Fixture> fixtures = new ArrayList<>();

    @OneToMany(mappedBy = "unit")
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

    public void setUnitAddress(String unitAddress){
        this.unitAddress = unitAddress;
    }

    public void setUnitNum(String unitNumber){
        this.unitNumber = unitNumber;
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

    public String getUnitAddress(){
        //if a unit does not have its own address, then it uses its property address
        if (this.unitAddress != null && !this.unitAddress.isEmpty()){
            return this.unitAddress;
        } else {
            return this.property.getAddress();
        }
    }

    public String getUnitNum(){
        return this.unitNumber;
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
    
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return this.unitId != null && this.unitId.equals(unit.getUnitId());
    }

    @Override
    public int hashCode(){
        return this.unitId != null ? this.unitId.hashCode() : 0;
    }
}