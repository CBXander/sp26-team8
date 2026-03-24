package com.sp26_team8.HelpRent.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.sp26_team8.HelpRent.repository.HelpGuideRepository;
import com.sp26_team8.HelpRent.entity.*;

@Service
public class HelpGuideService {
    private final HelpGuideRepository helpGuideRepository;
    private final FixtureService fixtureService;
    private final UnitService unitService;
    private final UserService userService;
    public HelpGuideService(HelpGuideRepository helpGuideRepository, FixtureService fixtureService, UnitService unitService,
                            UserService userService){
        this.helpGuideRepository = helpGuideRepository;
        this.fixtureService = fixtureService;
        this.unitService = unitService;
        this.userService = userService;
    }

//------------------------------------- POST METHODS -------------------------------------//
    //CREATE
    public HelpGuide createHelpGuide(HelpGuide helpGuide, Long fixtureId, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);
        if(fixtureId != null){
            helpGuide.setFixture(fixtureService.getFixtureById(fixtureId));
        } else {
            helpGuide.setFixture(null); //make sure if theres no fixtureId then its null so it counts as a general fixture
        }
        return helpGuideRepository.save(helpGuide);
    }

//------------------------------------- PUT METHODS -------------------------------------//
    //UPDATE
    public HelpGuide updateHelpGuide(Long helpGuideId, HelpGuide updatedHelpGuide, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);

        return helpGuideRepository.findById(helpGuideId).map(helpGuide->{
            helpGuide.setTitle(updatedHelpGuide.getTitle());
            helpGuide.setDescription(updatedHelpGuide.getDescription());
            helpGuide.setCategory(updatedHelpGuide.getCategory());

            return helpGuideRepository.save(helpGuide);
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "HelpGuide not found."));
    }

    //explicit update helpGuide's fixture
    public HelpGuide changeHelpGuideFixture(Long helpGuideId, Long fixtureId, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);
        HelpGuide guide = getHelpGuideById(helpGuideId);
        if(fixtureId != null){
            guide.setFixture(fixtureService.getFixtureById(fixtureId)); 
        } else {
            guide.setFixture(null);
        }
        return helpGuideRepository.save(guide);
    }

//------------------------------------- GET METHODS -------------------------------------//
    //READ
    public HelpGuide getHelpGuideById(Long id){
        return helpGuideRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "HelpGuide not found."));
    }
    //default get all helpguides
    public List<HelpGuide> getAllHelpGuides(){
        return helpGuideRepository.findAll();
    }
    //tenant view of helpGuides ###FOR TENANTS###
    public List<HelpGuide> getHelpGuidesForTenant(Long userId){
        userService.validateUserRole(userId, UserRole.TENANT);
        List<HelpGuide> helpGuides = new ArrayList<>();
        for(Fixture fixture : unitService.getUnitByTenant(userId).getFixtures()){
            helpGuides.addAll(helpGuideRepository.findByFixture(fixture));
        }
        return helpGuides;
    }
    //help guides by fixture
    public List<HelpGuide> getHelpGuideByFixture(Long fixtureId){
        return helpGuideRepository.findByFixture(fixtureService.getFixtureById(fixtureId));
    }
    //help guides by category
    public List<HelpGuide> getHelpGuideByCategory(String category){
        return helpGuideRepository.findByCategoryContainingIgnoreCase(category);
    }
//------------------------------------- DELETE METHODS -------------------------------------//
    //DELETE
    public void deleteHelpGuide(Long helpGuideId, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);

        if(!helpGuideRepository.existsById(helpGuideId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HelpGuide not found.");
        }
        helpGuideRepository.deleteById(helpGuideId);
    }
}
