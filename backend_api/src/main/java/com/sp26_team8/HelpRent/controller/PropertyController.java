package com.sp26_team8.HelpRent.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sp26_team8.HelpRent.entity.Property;
import com.sp26_team8.HelpRent.service.PropertyService;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {
    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    
    @PostMapping
    public ResponseEntity<Property> createProperty(@RequestBody Property property, @RequestParam Long userId) {
        return new ResponseEntity<>(propertyService.createProperty(property, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long propertyId, @RequestBody Property updatedProperty, @RequestParam Long userId) {
        return ResponseEntity.ok(propertyService.updateProperty(propertyId, updatedProperty, userId));
    }

    @PutMapping("/{propertyId}/transfer")
    public ResponseEntity<Property> transferPropertyOwnership(@PathVariable Long propertyId, @RequestParam Long userId, @RequestParam Long newLandlordId) {
        return ResponseEntity.ok(propertyService.transferPropertyOwnership(propertyId, userId, newLandlordId));
    }

    @PutMapping("/{propertyId}/staff/{staffId}")
    public ResponseEntity<Property> addStaffToProperty(@PathVariable Long propertyId, @PathVariable Long staffId, @RequestParam Long userId) {
        return ResponseEntity.ok(propertyService.addStaffToProperty(propertyId, staffId, userId));
    }

    @DeleteMapping("/{propertyId}/staff/{staffId}")
    public ResponseEntity<Property> removeStaffFromProperty(@PathVariable Long propertyId, @PathVariable Long staffId, @RequestParam Long userId) {
        return ResponseEntity.ok(propertyService.removeStaffFromProperty(propertyId, staffId, userId));
    }

    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long propertyId) {
        return ResponseEntity.ok(propertyService.getPropertyById(propertyId));
    }

    @GetMapping("/landlord")
    public ResponseEntity<List<Property>> getPropertiesByLandlord(@RequestParam Long userId) {
        return ResponseEntity.ok(propertyService.getPropertiesByLandlord(userId));
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long propertyId, @RequestParam Long userId) {
        propertyService.deleteProperty(propertyId, userId);
        return ResponseEntity.noContent().build();
    }
}
