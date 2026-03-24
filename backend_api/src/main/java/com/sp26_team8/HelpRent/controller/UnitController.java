package com.sp26_team8.HelpRent.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sp26_team8.HelpRent.entity.Unit;
import com.sp26_team8.HelpRent.service.UnitService;

@RestController
@RequestMapping("/api/units")
public class UnitController {
        private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    // POST /api/units?userId=1&propertyId=1
    @PostMapping("/")
    public ResponseEntity<Unit> createUnit(@RequestBody Unit unit, @RequestParam Long userId, @RequestParam Long propertyId) {
        return new ResponseEntity<>(unitService.createUnit(unit, userId, propertyId), HttpStatus.CREATED);
    }

    // PUT /api/units/{unitId}?userId=1
    @PutMapping("/{unitId}")
    public ResponseEntity<Unit> updateUnit(@PathVariable Long unitId, @RequestBody Unit updatedUnit, @RequestParam Long userId) {
        return ResponseEntity.ok(unitService.updateUnit(unitId, updatedUnit, userId));
    }

    // PUT /api/units/{unitId}/tenant/{tenantId}?userId=1
    @PutMapping("/{unitId}/tenant/{tenantId}")
    public ResponseEntity<Unit> addTenantToUnit(@PathVariable Long unitId, @PathVariable Long tenantId, @RequestParam Long userId) {
        return ResponseEntity.ok(unitService.addTenantToUnit(unitId, tenantId, userId));
    }

    // DELETE /api/units/{unitId}/tenant?userId=1
    @DeleteMapping("/{unitId}/tenant")
    public ResponseEntity<Unit> removeTenantFromUnit(@PathVariable Long unitId, @RequestParam Long userId) {
        return ResponseEntity.ok(unitService.removeTenantFromUnit(unitId, userId));
    }

    // PUT /api/units/{unitId}/fixtures/{fixtureId}?userId=1
    @PutMapping("/{unitId}/fixtures/{fixtureId}")
    public ResponseEntity<Unit> addFixtureToUnit(@PathVariable Long unitId, @PathVariable Long fixtureId, @RequestParam Long userId) {
        return ResponseEntity.ok(unitService.addFixtureToUnit(unitId, fixtureId, userId));
    }

    // DELETE /api/units/{unitId}/fixtures/{fixtureId}?userId=1
    @DeleteMapping("/{unitId}/fixtures/{fixtureId}")
    public ResponseEntity<Unit> removeFixtureFromUnit(@PathVariable Long unitId, @PathVariable Long fixtureId, @RequestParam Long userId) {
        return ResponseEntity.ok(unitService.removeFixtureFromUnit(unitId, fixtureId, userId));
    }

    // PUT /api/units/{unitId}/maintenance?userId=1
    @PutMapping("/{unitId}/maintenance")
    public ResponseEntity<Unit> setUnitUnderMaintenance(@PathVariable Long unitId, @RequestParam Long userId) {
        return ResponseEntity.ok(unitService.setUnitUnderMaintenace(unitId, userId));
    }

    // GET /api/units/{unitId}
    @GetMapping("/{unitId}")
    public ResponseEntity<Unit> getUnitById(@PathVariable Long unitId) {
        return ResponseEntity.ok(unitService.getUnitById(unitId));
    }

    // GET /api/units
    @GetMapping
    public ResponseEntity<List<Unit>> getAllUnits() {
        return ResponseEntity.ok(unitService.getAllUnits());
    }

    // GET /api/units/property/{propertyId}?userId=1
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Unit>> getUnitsByProperty(@PathVariable Long propertyId, @RequestParam Long userId) {
        return ResponseEntity.ok(unitService.getUnitsByProperty(propertyId, userId));
    }

    // GET /api/units/tenant?userId=1
    @GetMapping("/tenant")
    public ResponseEntity<Unit> getUnitByTenant(@RequestParam Long userId) {
        return ResponseEntity.ok(unitService.getUnitByTenant(userId));
    }

    // DELETE /api/units/{unitId}?userId=1
    @DeleteMapping("/{unitId}")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long unitId, @RequestParam Long userId) {
        unitService.deleteUnit(unitId, userId);
        return ResponseEntity.noContent().build();
    }
}
