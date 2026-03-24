package com.sp26_team8.HelpRent.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    //User class, used for all user types, enum gates access to various services
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String passwordHash;
    
    @Column
    private String firstName;
    
    @Column
    private String lastName;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

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
    public void setEmail(String email){
        this.email = email;
    }

    // password stuff that will be covered in next class
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setStatus(UserStatus status){
        this.status = status;
    }

    public void setRole(UserRole role){
        this.role = role;
    }
    
    //getters 
    public Long getUserId(){
        return this.userId;
    }

    public String getEmail(){
        return this.email;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getFullName(){
        return this.firstName + " " + this.lastName; 
    }

    public UserStatus getStatus(){
        return this.status;
    }

    public UserRole getRole(){
        return this.role;
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
        User user = (User) o;
        return this.userId != null && this.userId.equals(user.getUserId());
    }

    @Override
    public int hashCode(){
        return this.userId != null ? this.userId.hashCode() : 0;
    }
}