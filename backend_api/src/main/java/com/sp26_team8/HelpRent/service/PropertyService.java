package com.sp26_team8.HelpRent.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.sp26_team8.HelpRent.repository.PropertyRepository;
import com.sp26_team8.HelpRent.entity.*;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;
    public PropertyService(PropertyRepository propertyRepository){
        this.propertyRepository = propertyRepository;
    }

    //CRUD REQUIREMENTS
    public List<Property> getAllProperties(){
        return propertyRepository.findAll();
    }

    //CREATE
    public Property createProperty(Property property){
        if (propertyRepository.findByAddress(property.getAddress()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A property with this address already exists.");
        }
        return propertyRepository.save(property);
    }

    //UPDATE
    public Property updateProperty(Long id, Property updatedProperty){
        return propertyRepository.findById(id).map(property->{
            property.setLandlord(updatedProperty.getLandlord());
            property.setName(updatedProperty.getName());
            property.setAddress(updatedProperty.getAddress());
            
            return propertyRepository.save(property);
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found."));
    }

    //READ
    public Property getPropertyById(Long id){
        return propertyRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found."));
    }

    //DELETE
    public void deleteProperty(Long id){
        if(!propertyRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found.");
        }
        propertyRepository.deleteById(id);
    }

    //PROPERTY SERVICES

}
