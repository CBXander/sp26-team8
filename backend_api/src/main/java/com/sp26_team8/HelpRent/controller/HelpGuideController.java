package com.sp26_team8.HelpRent.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sp26_team8.HelpRent.entity.HelpGuide;
import com.sp26_team8.HelpRent.service.HelpGuideService;

@RestController
@RequestMapping("/api/helpGuides")
public class HelpGuideController {
    private final HelpGuideService helpGuideService;

    public HelpGuideController (HelpGuideService helpGuideService){
        this.helpGuideService = helpGuideService;
    }

    @PostMapping
    public ResponseEntity<HelpGuide> createHelpGuide(@RequestBody HelpGuide helpguide, @RequestParam(required = false) Long fixtureId,  @RequestParam Long userId){
        return new ResponseEntity<>(helpGuideService.createHelpGuide(helpguide, fixtureId, userId),HttpStatus.CREATED);
    }


    @PutMapping("/{helpGuideId}")
    public ResponseEntity<HelpGuide> updateHelpGuide(@PathVariable Long helpGuideId, @RequestBody HelpGuide updatedHelpGuid, @RequestParam Long userId){
        return ResponseEntity.ok(helpGuideService.updateHelpGuide(helpGuideId, updatedHelpGuid, userId));
    }

    @PutMapping("/{helpGuideId}/transfer_fixture")
    public ResponseEntity<HelpGuide> changeHelpGuideFixture(@PathVariable Long helpGuideId, @RequestParam(required = false) Long fixtureId, @RequestParam Long userId){
        return ResponseEntity.ok(helpGuideService.changeHelpGuideFixture(helpGuideId, fixtureId, userId));
    }

    @GetMapping
    public ResponseEntity<List<HelpGuide>> getAllHelpGuides(){
        return ResponseEntity.ok(helpGuideService.getAllHelpGuides());
    }
    
    @GetMapping("/{helpGuideId}")
    public ResponseEntity<HelpGuide> getHelpGuideById(@PathVariable Long helpGuideId){
        return ResponseEntity.ok(helpGuideService.getHelpGuideById(helpGuideId));
    }

    @GetMapping("/tenantView")
    public ResponseEntity<List<HelpGuide>> getHelpGuidesForTenant(@RequestParam Long userId){
        return ResponseEntity.ok(helpGuideService.getHelpGuidesForTenant(userId));
    }

    @GetMapping("/fixture/{fixtureId}")
    public ResponseEntity<List<HelpGuide>> getHelpGuideByFixture(@PathVariable Long fixtureId){
        return ResponseEntity.ok(helpGuideService.getHelpGuideByFixture(fixtureId));
    }

    @GetMapping("/category")
    public ResponseEntity<List<HelpGuide>> getHelpGuideByCategory(@RequestParam String category){
        return ResponseEntity.ok(helpGuideService.getHelpGuideByCategory(category));
    }

    @DeleteMapping("/{helpGuideId}")
    public ResponseEntity<Void> deleteHelpGuide(@PathVariable Long helpGuideId, @RequestParam Long userId){
        helpGuideService.deleteHelpGuide(helpGuideId, userId);
        return ResponseEntity.noContent().build();
    }
}
