package com.sp26_team8.HelpRent.repository;

import java.util.List;
import java.time.LocalDateTime;

import com.sp26_team8.HelpRent.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>{
    
    Property findByAddress(String address);

    List<Property> findByLandlord(User landlord);
    
    List<Property> findByStaff_UserId(Long userId);

    List<Property> findByNameContainingIgnoreCase(String name);
    List<Property> findByCreatedAt(LocalDateTime createdAt);
    List<Property> findByUpdatedAt(LocalDateTime updatedAt);
}