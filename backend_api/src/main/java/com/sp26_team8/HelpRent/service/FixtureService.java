package com.sp26_team8.HelpRent.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.sp26_team8.HelpRent.repository.FixtureRepository;
import com.sp26_team8.HelpRent.repository.UnitRepository;
import com.sp26_team8.HelpRent.entity.*;

@Service
public class FixtureService {
    private final FixtureRepository fixtureRepository;
    private final UserService userService;
    private final UnitRepository unitRepository;

    public FixtureService(FixtureRepository fixtureRepository, UserService userService, UnitRepository unitRepository){
        this.fixtureRepository = fixtureRepository;
        this.userService = userService;
        this.unitRepository = unitRepository;
    }

//------------------------------------- POST METHODS -------------------------------------//
    //Create new fixture ###FOR LANDLORDS###
    public Fixture createFixture(Fixture fixture, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);

        return fixtureRepository.save(fixture);
    }

//------------------------------------- PUT METHODS -------------------------------------//
    //UPDATE ###FOR LANDLORDS###
    public Fixture updateFixture(Long fixtureId, Fixture updatedFixture, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);
        return fixtureRepository.findById(fixtureId).map(fixture->{
            fixture.setTitle(updatedFixture.getTitle());
            fixture.setDescription(updatedFixture.getDescription());
            
            return fixtureRepository.save(fixture);
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fixture not found."));
    }



//------------------------------------- GET METHODS -------------------------------------//
    //READ
    public Fixture getFixtureById(Long fixtureId){
        return fixtureRepository.findById(fixtureId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fixture not found."));
    }

    //default get all 
    public List<Fixture> getAllFixtures(){
        return fixtureRepository.findAll();
    }

    //get fixtures by unit ###FOR LANDLORDS### ###FOR TENANTS###
    public List<Fixture> getFixturesByUnit(Long unitId, Long userId){
        User user = userService.validateUserRole(userId, UserRole.LANDLORD, UserRole.TENANT);
        Unit unit = unitRepository.findById(unitId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found.")
            );
        if (user.getRole().equals(UserRole.LANDLORD)){
            if(!unit.getProperty().getLandlord().getUserId().equals(userId)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unit does not belong to your property.");
            }
        }else if (user.getRole().equals(UserRole.TENANT)){
            if(unit.getTenant() == null || !unit.getTenant().getUserId().equals(userId)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unit belongs to another tenant");
            }
        }

        return unit.getFixtures();
    }

    public List<Fixture> getFixtureByTitle(String title){
        return fixtureRepository.findByTitleContainingIgnoreCase(title);
    }

//------------------------------------- DELETE METHODS -------------------------------------//   
    //DELETE ###FOR LANDLORDS###
    public void deleteFixture(Long fixtureId, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);

        if (!fixtureRepository.existsById(fixtureId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fixture not found.");
        }

        fixtureRepository.deleteById(fixtureId);
    }
}
