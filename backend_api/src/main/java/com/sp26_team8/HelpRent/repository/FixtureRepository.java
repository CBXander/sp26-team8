package com.sp26_team8.HelpRent.repository;

import java.util.List;
import java.time.LocalDateTime;

import com.sp26_team8.HelpRent.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixtureRepository extends JpaRepository<Fixture, Long>{
    List<Fixture> findByTitleContainingIgnoreCase(String name);
    List<Fixture> findByCreatedAt(LocalDateTime createdAt);
    List<Fixture> findByUpdatedAt(LocalDateTime updatedAt);
}