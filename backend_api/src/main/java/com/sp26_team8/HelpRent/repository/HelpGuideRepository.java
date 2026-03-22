package com.sp26_team8.HelpRent.repository;

import java.util.List;
import java.time.LocalDateTime;

import com.sp26_team8.HelpRent.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpGuideRepository extends JpaRepository<Property, Long>{
    List<HelpGuide> findByName(String name);
    List<HelpGuide> findByCategoryContainingIgnoreCase(String category)
    List<HelpGuide> findByFixture(Fixture fixture);
    List<HelpGuide> findByDateCreated(LocalDateTime createdAt);
    List<HelpGuide> findByDateUpdated(LocalDateTime updatedAt);
}