package com.sp26_team8.HelpRent.repository;
import java.util.List;
import java.time.LocalDateTime;

import com.sp26_team8.HelpRent.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>{
    Unit findByTenant(User tenant);

    List<Unit> findByProperty(Property property);
    List<Unit> findByUnitAddressIgnoreCase(String address);
    List<Unit> findByUnitNumberIgnoreCase(String unitNumber);
    List<Unit> findByUnitAddressAndUnitNumberAllIgnoreCase(String unitAddress, String unitNum);

    List<Unit> findByFixtures_FixtureId(Long fixtureId);

    List<Unit> findByStatus(UnitStatus unitStatus);

    List<Unit> findByCreatedAt(LocalDateTime createdAt);
    List<Unit> findByUpdatedAt(LocalDateTime updatedAt);
} 