package com.sp26_team8.HelpRent.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.sp26_team8.HelpRent.repository.*;
import com.sp26_team8.HelpRent.service.*;

import tools.jackson.databind.annotation.JsonAppend.Prop;

import com.sp26_team8.HelpRent.entity.*;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final UserService userService;
    public PropertyService(PropertyRepository propertyRepository, UserService userService){
        this.propertyRepository = propertyRepository;
        this.userService = userService;
    }

    //VERIFY LANDLORD-PROPERTY OWNERSHIP 
    private Property verifyLandlordOwnership(Long propertyId, Long userId){
        Property property = propertyRepository.findById(propertyId).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found.")
        );
        if(!property.getLandlord().getUserId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This property does not belong to you.");
        }
        return property;
    }

//------------------------------------- POST METHODS -------------------------------------//
    //default property create
    public Property createProperty(Property property, Long userId){
        if (propertyRepository.findByAddress(property.getAddress()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A property with this address already exists.");
        }
        //check permissions and ownership
        User landlord = userService.validateUserRole(userId, UserRole.LANDLORD);  //if user is landlord, function proceeds
        property.setLandlord(landlord);
        
        return propertyRepository.save(property);
    }

//------------------------------------- PUT METHODS -------------------------------------//
    //default property update
    public Property updateProperty(Long propertyId, Property updatedProperty, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);    // verify user is a landlord
        Property property = verifyLandlordOwnership(propertyId, userId);    //verify property belongs to user
        property.setName(updatedProperty.getName());
        property.setAddress(updatedProperty.getAddress());
        
        return propertyRepository.save(property);
    }
    
    //transfer ownership of property
    public Property transferPropertyOwnership(Long propertyId, Long userId, Long newLandlordId){
        userService.validateUserRole(userId, UserRole.LANDLORD);
        User newLandlord = userService.validateUserRole(newLandlordId, UserRole.LANDLORD);
        Property property = verifyLandlordOwnership(propertyId, userId);
        property.setLandlord(newLandlord);
        
        return propertyRepository.save(property);
    }

    //add new staff to property
    public Property addStaffToProperty(Long propertyId, Long staffId, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);
        User newStaff = userService.validateUserRole(staffId, UserRole.MAINTENANCE);
        Property property = verifyLandlordOwnership(propertyId, userId);
        if(property.getStaff().contains(newStaff)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This staff member is already assigned to this property");
        }
        property.getStaff().add(newStaff);

        return propertyRepository.save(property);
    }

    //remove staff from property
    public Property removeStaffFromProperty(Long propertyId, Long staffId, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);
        User staff = userService.validateUserRole(staffId, UserRole.MAINTENANCE);
        Property property = verifyLandlordOwnership(propertyId, userId);
        if(!property.getStaff().contains(staff)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user is not a part of this property's staff");
        }
        property.getStaff().remove(staff);

        return propertyRepository.save(property);
    }

//------------------------------------- GET METHODS -------------------------------------//
    //default get (all)
    public List<Property> getAllProperties(){
        return propertyRepository.findAll();
    }
    //default get (by id)
    public Property getPropertyById(Long id){
        return propertyRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found."));
    }

    //Get all properties belonging to a landlord
    public List<Property> getPropertiesByLandlord(Long userId){
        User landlord = userService.validateUserRole(userId, UserRole.LANDLORD);
        
        return propertyRepository.findByLandlord(landlord);
    }

//------------------------------------- DELETE METHODS -------------------------------------//
    public void deleteProperty(Long propertyId, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);
        Property property = verifyLandlordOwnership(propertyId, userId);
        
        //TODO: handle floating links to units amd staff
        propertyRepository.delete(property);
    }

}
