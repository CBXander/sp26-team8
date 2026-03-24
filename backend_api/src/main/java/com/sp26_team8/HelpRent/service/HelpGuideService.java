package com.sp26_team8.HelpRent.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.sp26_team8.HelpRent.repository.HelpGuideRepository;
import com.sp26_team8.HelpRent.entity.*;

@Service
public class HelpGuideService {
    private final HelpGuideRepository helpGuideRepository;
    public HelpGuideService(HelpGuideRepository helpGuideRepository){
        this.helpGuideRepository = helpGuideRepository;
    }

//------------------------------------- POST METHODS -------------------------------------//
    //CREATE
    public HelpGuide createHelpGuide(HelpGuide helpGuide){
        return helpGuideRepository.save(helpGuide);
    }

//------------------------------------- PUT METHODS -------------------------------------//
    //UPDATE
    public HelpGuide updateHelpGuide(Long id, HelpGuide updatedHelpGuide){
        return helpGuideRepository.findById(id).map(helpGuide->{
            helpGuide.setTitle(updatedHelpGuide.getTitle());
            helpGuide.setDescription(updatedHelpGuide.getDescription());
            helpGuide.setCategory(updatedHelpGuide.getCategory());
            helpGuide.setFixture(updatedHelpGuide.getFixture());

            return helpGuideRepository.save(helpGuide);
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "HelpGuide not found."));
    }

//------------------------------------- GET METHODS -------------------------------------//
    //READ
    public HelpGuide getHelpGuideById(Long id){
        return helpGuideRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "HelpGuide not found."));
    }

    public List<HelpGuide> getAllHelpGuides(){
        return helpGuideRepository.findAll();
    }

//------------------------------------- DELETE METHODS -------------------------------------//
    //DELETE
    public void deleteHelpGuide(Long id){
        if(!helpGuideRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HelpGuide not found.");
        }
        helpGuideRepository.deleteById(id);
    }
}
