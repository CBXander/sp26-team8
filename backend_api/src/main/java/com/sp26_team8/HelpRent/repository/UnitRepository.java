package com.sp26_team8.HelpRent.repository;
import java.util.List;
import java.time.LocalDateTime;

import com.sp26_team8.HelpRent.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>{
    Unit findByTenant(User tenant);
    Unit findByDateCreated(LocalDateTime createdAt);
    Unit findByDateUpdated(LocalDateTime updatedAt);

    List<Unit> findByProperty(Property property);
    List<Unit> findByAddressIgnoreCase(String address);
    List<Unit> findByNumberIgnoreCase(String unitNum);
    List<Unit> findByAdressAndNumberIgnoreCase(String unitAddress, String unitNum);

    List<Unit> findByFixture(Fixture fixture);
    List<Unit> findByStatus(UnitStatus status);
} 