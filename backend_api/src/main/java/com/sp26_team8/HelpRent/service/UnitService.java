package com.sp26_team8.HelpRent.service;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sp26_team8.HelpRent.entity.Fixture;
import com.sp26_team8.HelpRent.entity.Property;
import com.sp26_team8.HelpRent.entity.Unit;
import com.sp26_team8.HelpRent.entity.UnitStatus;
import com.sp26_team8.HelpRent.entity.User;
import com.sp26_team8.HelpRent.entity.UserRole;
import com.sp26_team8.HelpRent.repository.UnitRepository;

@Service
public class UnitService {
    private final UnitRepository unitRepository;
    private final UserService userService;
    private final PropertyService propertyService;
    private final FixtureService fixtureService;

    public UnitService(UnitRepository unitRepository, UserService userService, 
                       PropertyService propertyService, FixtureService fixtureService){
        this.unitRepository = unitRepository;
        this.userService = userService;
        this.propertyService = propertyService;
        this.fixtureService = fixtureService;
    }


    public Unit verifyLandlordUnitOwnership(Long userId, Long unitId){
        Unit unit = unitRepository.findById(unitId).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found.")
        );
        if(!unit.getProperty().getLandlord().getUserId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This unit does not belong to you.");
        }
        return unit;
    }

    private Unit validateUnitLandlord(Long unitId, Long userId){
        //all put methods for landlord requires these two calls and needs a Unit:
        userService.validateUserRole(userId, UserRole.LANDLORD);
        return verifyLandlordUnitOwnership(userId, unitId);
    }
//------------------------------------- POST METHODS -------------------------------------//
    //create ###FOR LANDLORDS###
    public Unit createUnit(Unit unit , Long userId, Long propertyId){
        userService.validateUserRole(userId, UserRole.LANDLORD);
        
        Property property = propertyService.verifyLandlordOwnership(propertyId, userId);
        unit.setProperty(property); //make sure the property is correct 
        unit.setUnitStatus(UnitStatus.VACANT);
        if(unit.getUnitNum() == null){
            // non-appartments have no unit number, SINGLE represents detached housing like this
            unit.setUnitNum("SINGLE");
        }
        if(unit.getUnitAddress()== null){
            // unit address if null should be property addres (unit is in same building as property)
            unit.setUnitAddress(property.getAddress());
        }

        List<Unit> existing = unitRepository.findByUnitAddressAndUnitNumberAllIgnoreCase(unit.getUnitAddress(), unit.getUnitNum());
        if (!existing.isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A unit with this address and unit number already exists.");
        }
        
        

        return unitRepository.save(unit);
    }

//------------------------------------- PUT METHODS -------------------------------------//
    //update ###FOR LANDLORDS###
    public Unit updateUnit(Long unitId, Unit updatedUnit, Long userId){
        Unit unit = validateUnitLandlord(unitId, userId);
        
        unit.setUnitAddress(updatedUnit.getUnitAddress());
        unit.setUnitNum(updatedUnit.getUnitNum());
        
        return unitRepository.save(unit);
    }

    //assign tenant to unit ###FOR LANDLORDS###
    public Unit addTenantToUnit(Long unitId, Long tenantId, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);
        Unit unit = verifyLandlordUnitOwnership(userId, unitId);

        unit.setTenant(userService.validateUserRole(tenantId, UserRole.TENANT));
        unit.setUnitStatus(UnitStatus.LEASED);
        return unitRepository.save(unit);
    }

    //remove tenant from unit ###FOR LANDLORDS###
    public Unit removeTenantFromUnit(Long unitId, Long userId){
        Unit unit = validateUnitLandlord(unitId, userId);
        
        if(unit.getTenant() == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This unit is already vacant.");
        }

        unit.setTenant(null);
        unit.setUnitStatus(UnitStatus.VACANT);

        return unitRepository.save(unit);
    }

    //add fixture to unit ###FOR LANDLORDS###
    public Unit addFixtureToUnit(Long unitId, Long fixtureId, Long userId){
        Unit unit = validateUnitLandlord(unitId, userId);
        Fixture newFixture = fixtureService.getFixtureById(fixtureId);
        if (unit.getFixtures().contains(newFixture)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This unit already has this fixture.");
        }

        unit.getFixtures().add(newFixture);

        return unitRepository.save(unit);
    }

    //remove fixture from unit ###FOR LANDLORDS###
    public Unit removeFixtureFromUnit(Long unitId, Long fixtureId, Long userId){
        Unit unit = validateUnitLandlord(unitId, userId);
        Fixture fixture = fixtureService.getFixtureById(fixtureId);
        if(!unit.getFixtures().contains(fixture)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This unit does not have this fixture.");
        }

        unit.getFixtures().remove(fixture);

        return unitRepository.save(unit);
    }

    //update a unit to be undergoing maintenance
    //in the future we may want to change this to have another helper that lets landlord move where tenant is leasing while unit is
    //undergoing maintenance since that status should be reserved for a more larger maintenance issue like a renovation or a serious
    //issue with the unit.
    public Unit setUnitUnderMaintenace(Long unitId, Long userId){
        Unit unit = validateUnitLandlord(unitId, userId);
        if(unit.getUnitStatus().equals(UnitStatus.MAINTENANCE)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This unit's status is already MAINTENANCE.");
        }  
        unit.setUnitStatus(UnitStatus.MAINTENANCE);
        return unitRepository.save(unit);
    }

//------------------------------------- GET METHODS -------------------------------------//
    // read by id
    public Unit getUnitById(Long id){
        return unitRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found."));
    }
    
    // Read all  
    public List<Unit> getAllUnits(){
        return unitRepository.findAll();
    }

    //get all units under a property ###FOR LANDLORDS###
    public List<Unit> getUnitsByProperty(Long propertyId, Long userId){
        userService.validateUserRole(userId, UserRole.LANDLORD);
        Property property = propertyService.verifyLandlordOwnership(propertyId, userId);

        return unitRepository.findByProperty(property);
    }

    //get unnits by status
    public List<Unit> getUnitsByStatus(UnitStatus status){
        return unitRepository.findByStatus(status);
    }

    //get tenant's unit ###FOR TENANTS###
    public Unit getUnitByTenant(Long userId){
        User user = userService.validateUserRole(userId, UserRole.TENANT);
        Unit unit = unitRepository.findByTenant(user);
        if (unit == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No unit assigned to this user.");
        }

        return unit;
    }

//------------------------------------- DELETE METHODS -------------------------------------//
    //delete
    public void deleteUnit(Long unitId, Long userId){
        Unit unit = validateUnitLandlord(unitId, userId);
        
        //TODO: handle floating links to Tenant, Fixtures, and tickets
        unitRepository.delete(unit);
    }
}
