package com.sp26_team8.HelpRent.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sp26_team8.HelpRent.entity.Fixture;
import com.sp26_team8.HelpRent.service.FixtureService;

@RestController
@RequestMapping("/api/fixtures")
public class FixtureController {
    private final FixtureService fixtureService;

    public FixtureController(FixtureService fixtureService){
        this.fixtureService = fixtureService;
    }

    @PostMapping
    public ResponseEntity<Fixture> createFixture(@RequestBody Fixture fixture,@RequestParam Long userId){
        return new ResponseEntity<>(fixtureService.createFixture(fixture, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{fixtureId}")
    public ResponseEntity<Fixture> updateFixture(@PathVariable Long fixtureId, @RequestBody Fixture updatedFixture, @RequestParam Long userId){
        return ResponseEntity.ok(fixtureService.updateFixture(fixtureId, updatedFixture, userId));
    }

    @GetMapping
    public ResponseEntity<List<Fixture>> getAllFixtures(){
        return ResponseEntity.ok(fixtureService.getAllFixtures());
    }

    @GetMapping("/{fixtureId}")
    public ResponseEntity<Fixture> getFixtureById(@PathVariable Long fixtureId){
        return ResponseEntity.ok(fixtureService.getFixtureById(fixtureId));
    }

    @GetMapping("/unit/{unitId}")
    public ResponseEntity<List<Fixture>> getFixturesByUnit(@PathVariable Long unitId, @RequestParam Long userId){
        return ResponseEntity.ok(fixtureService.getFixturesByUnit(unitId, userId));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Fixture>> getFixtureByTitle(@PathVariable String title){
        return ResponseEntity.ok(fixtureService.getFixtureByTitle(title));
    }

    @DeleteMapping("/{fixtureId}")
    public ResponseEntity<Void> deleteFixture(@PathVariable Long fixtureId, @RequestParam Long userId){
        fixtureService.deleteFixture(fixtureId, userId);
        return ResponseEntity.noContent().build();
    }
}
