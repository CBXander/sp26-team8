package com.sp26_team8.HelpRent.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyId;

    @ManyToOne
    @JoinColumn(name="landlord_id", nullable = false)
    @JsonBackReference("landlord-properties")
    private User landlord;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String address;

    @OneToMany(mappedBy = "property")
    @JsonManagedReference("property-units")
    private List<Unit> units = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "property_staff",
        joinColumns = @JoinColumn(name="property_id"),
        //staff_id is actually a user_id just changed the name to differentiate between user roles since this would only have staff
        inverseJoinColumns = @JoinColumn(name="staff_id") 
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler",})
    private List<User> staff = new ArrayList<>();

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

    //Setters
    public void setLandlord(User landlord){
        this.landlord = landlord;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setUnits(List<Unit> units){
        this.units = units;
    }

    public void setStaff(List<User> staff){
        this.staff = staff;
    }


    //Getters
    public Long getPropertyId(){
        return this.propertyId;
    }

    public User getLandlord(){
        return this.landlord;
    }

    public String getName(){
        return this.name;
    }

    public String getAddress(){
        return this.address;
    }

    public List<Unit> getUnits(){
        return this.units;
    }


    public List<User> getStaff(){
        return this.staff;
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
        Property property = (Property) o;
        return this.propertyId != null && this.propertyId.equals(property.getPropertyId());
    }

    @Override
    public int hashCode(){
        return this.propertyId != null ? this.propertyId.hashCode() : 0;
    }
}