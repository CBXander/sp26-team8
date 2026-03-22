package com.sp26_team8.HelpRent.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.sp26_team8.HelpRent.repository.FixtureRepository;
import com.sp26_team8.HelpRent.entity.*;

@Service
public class FixtureService {
    private final FixtureRepository fixtureRepository;
    public FixtureService(FixtureRepository fixtureRepository){
        this.fixtureRepository = fixtureRepository;
    }

    //CRUD REQUIRED
    public List<Fixture> getAllFixtures(){
        return fixtureRepository.findAll();
    }

    //CREATE
    public Fixture createFixture(Fixture fixture){
        return fixtureRepository.save(fixture);
    }

    //UPDATE
    public Fixture updateFixture(Long id, Fixture updatedFixture){
        return fixtureRepository.findById(id).map(fixture->{
            fixture.setTitle(updatedFixture.getTitle());
            fixture.setDescription(updatedFixture.getDescription());
            
            return fixtureRepository.save(fixture);
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fixture not found."));
    }

    //READ
    public Fixture getFixtureById(Long id){
        return fixtureRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fixture not found."));
    }

    //DELETE
    public void deleteFixture(Long id){
        if (!fixtureRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fixture not found.");
        }
        fixtureRepository.deleteById(id);
    }

    //FIXTURE SERVICES
}
