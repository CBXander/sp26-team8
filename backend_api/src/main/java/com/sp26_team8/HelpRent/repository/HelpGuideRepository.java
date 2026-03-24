package com.sp26_team8.HelpRent.repository;

import java.util.List;
import java.time.LocalDateTime;

import com.sp26_team8.HelpRent.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpGuideRepository extends JpaRepository<HelpGuide, Long>{
    List<HelpGuide> findByTitleContainingIgnoreCase(String title);
    List<HelpGuide> findByCategoryContainingIgnoreCase(String category);
    List<HelpGuide> findByFixture(Fixture fixture);
    List<HelpGuide> findByCreatedAt(LocalDateTime createdAt);
    List<HelpGuide> findByUpdatedAt(LocalDateTime updatedAt);
}