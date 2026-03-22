package com.sp26_team8.HelpRent.repository;

import java.util.List;
import java.time.LocalDateTime;

import com.sp26_team8.HelpRent.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixtureRepository extends JpaRepository<Property, Long>{
    List<Fixture> findByCategoryContainingIgnoreCase(String name);
    List<Fixture> findByDateCreated(LocalDateTime createdAt);
    List<Fixture> findByDateUpdated(LocalDateTime updatedAt);
}