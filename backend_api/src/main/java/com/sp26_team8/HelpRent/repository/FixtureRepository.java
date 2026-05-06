package com.sp26_team8.HelpRent.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sp26_team8.HelpRent.entity.Fixture;
import com.sp26_team8.HelpRent.entity.Property;

@Repository
public interface FixtureRepository extends JpaRepository<Fixture, Long>{
    List<Fixture> findByTitleContainingIgnoreCase(String name);
    List<Fixture> findByProperty(Property property);
    List<Fixture> findByCreatedAt(LocalDateTime createdAt);
    List<Fixture> findByUpdatedAt(LocalDateTime updatedAt);
}