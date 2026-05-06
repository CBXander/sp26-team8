package com.sp26_team8.HelpRent.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sp26_team8.HelpRent.entity.Property;
import com.sp26_team8.HelpRent.entity.User;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>{
    
    Property findByAddress(String address);
    Property findByLandlord(User landlord);

    List<Property> findByStaff_UserId(Long userId);
    List<Property> findByNameContainingIgnoreCase(String name);
    List<Property> findByCreatedAt(LocalDateTime createdAt);
    List<Property> findByUpdatedAt(LocalDateTime updatedAt);
}