package com.sp26_team8.HelpRent.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sp26_team8.HelpRent.entity.Unit;
import com.sp26_team8.HelpRent.entity.UnitStatus;
import com.sp26_team8.HelpRent.service.UnitService;

@RestController
@RequestMapping("/api/units")
public class UnitController {
        private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseEntity<Unit> createUnit(@RequestBody Unit unit, @RequestParam Long userId, @RequestParam Long propertyId) {
        return new ResponseEntity<>(unitService.createUnit(unit, userId, propertyId), HttpStatus.CREATED);
    }

    @PutMapping("/{unitId}")
    public ResponseEntity<Unit> updateUnit(@PathVariable Long unitId, @RequestBody Unit updatedUnit, @RequestParam Long userId) {
        return ResponseEntity.ok(unitService.updateUnit(unitId, updatedUnit, userId));
    }

    @PutMapping("/{unitId}/tenant/{tenantId}")
    public ResponseEntity<Unit> addTenantToUnit(@PathVariable Long unitId, @PathVariable Long tenantId, @RequestParam Long userId) {
        return ResponseEntity.ok(unitService.addTenantToUnit(unitId, tenantId, userId));
    }

    @DeleteMapping("/{unitId}/tenant")
    public ResponseEntity<Unit> removeTenantFromUnit(@PathVariable Long unitId, @RequestParam Long userId) {
        return ResponseEntity.ok(unitService.removeTenantFromUnit(unitId, userId));
    }

    @PutMapping("/{unitId}/fixtures/{fixtureId}")
    public ResponseEntity<Unit> addFixtureToUnit(@PathVariable Long unitId, @PathVariable Long fixtureId, @RequestParam Long userId) {
        return ResponseEntity.ok(unitService.addFixtureToUnit(unitId, fixtureId, userId));
    }

    @DeleteMapping("/{unitId}/fixtures/{fixtureId}")
    public ResponseEntity<Unit> removeFixtureFromUnit(@PathVariable Long unitId, @PathVariable Long fixtureId, @RequestParam Long userId) {
        return ResponseEntity.ok(unitService.removeFixtureFromUnit(unitId, fixtureId, userId));
    }

    @PutMapping("/{unitId}/maintenance")
    public ResponseEntity<Unit> setUnitUnderMaintenance(@PathVariable Long unitId, @RequestParam Long userId) {
        return ResponseEntity.ok(unitService.setUnitUnderMaintenace(unitId, userId));
    }

    @GetMapping("/{unitId}")
    public ResponseEntity<Unit> getUnitById(@PathVariable Long unitId) {
        return ResponseEntity.ok(unitService.getUnitById(unitId));
    }

    @GetMapping
    public ResponseEntity<List<Unit>> getAllUnits() {
        return ResponseEntity.ok(unitService.getAllUnits());
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Unit>> getUnitsByProperty(@PathVariable Long propertyId, @RequestParam Long userId) {
        return ResponseEntity.ok(unitService.getUnitsByProperty(propertyId, userId));
    }

    @GetMapping("/tenant")
    public ResponseEntity<Unit> getUnitByTenant(@RequestParam Long userId) {
        return ResponseEntity.ok(unitService.getUnitByTenant(userId));
    }

    @GetMapping("/status")
    public ResponseEntity<List<Unit>> getUnitsByStatus(@RequestParam String status) {
        return ResponseEntity.ok(
            unitService.getUnitsByStatus(UnitStatus.valueOf(status.toUpperCase()))
        );
    }
    @DeleteMapping("/{unitId}")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long unitId, @RequestParam Long userId) {
        unitService.deleteUnit(unitId, userId);
        return ResponseEntity.noContent().build();
    }
}
