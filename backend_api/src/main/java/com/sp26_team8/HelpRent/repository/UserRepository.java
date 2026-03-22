package com.sp26_team8.HelpRent.repository;

import java.util.List;
import java.time.LocalDateTime;

import com.sp26_team8.HelpRent.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    List<User> findByFirstNameContainingIgnoreCase(String first);
    List<User> findByLastNameContainingIgnoreCase(String last);
    User findByEmaiIgnoreCase(String email);
    
    List<User> findByRole(UserRole role);
    List<User> findByStatus(UserStatus status);
    
    List<User> findByDateCreated(LocalDateTime createdAt);
    List<User> findByDateUpdated(LocalDateTime updatedAt);
}
