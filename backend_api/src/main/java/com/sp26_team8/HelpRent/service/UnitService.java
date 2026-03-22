package com.sp26_team8.HelpRent.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.sp26_team8.HelpRent.repository.UnitRepository;
import com.sp26_team8.HelpRent.entity.*;

@Service
public class UnitService {
    private final UnitRepository unitRepository;
    public UnitService(UnitRepository unitRepository){
        this.unitRepository = unitRepository;
    }

    //CRUD REQUIRED
    public List<Unit> getAllUnits(){
        return unitRepository.findAll();
    }

    //create
    public Unit createUnit(Unit unit){
        if(unit.getUnitNum() == null){
            // non-appartments have no unit number, SINGLE represents and detached housing like this
            unit.setUnitNum("SINGLE");
        }
        if(unit.getUnitAdress()== null){
            // unit address if null should be property addres (unit is in same building as property)
            unit.setUnitAdress(unit.getProperty().getAddress());
        }

        //temp vars for duplicate unit check 
        String address = unit.getUnitAdress();
        String unitNumber = unit.getUnitNum();
        List<Unit> existing = unitRepository.findByUnitAddressAndUnitNumberAllIgnoreCase(address, unitNumber);

        if (!existing.isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A unit with this address and unit number already exists.");
        }

        return unitRepository.save(unit);
    }

    //update
    public Unit updateUnit(Long id, Unit updatedUnit){
        return unitRepository.findById(id).map(unit->{
            unit.setProperty(updatedUnit.getProperty());
            unit.setTenant(updatedUnit.getTenant());
            unit.setUnitStatus(updatedUnit.getUnitStatus());
            unit.setUnitAdress(updatedUnit.getUnitAdress());
            unit.setUnitNum(updatedUnit.getUnitNum());

            return unitRepository.save(unit);
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found."));
        
    }

    //read
    public Unit getUnitById(Long id){
        return unitRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found."));
    }

    //delete
    public void deleteUnit(Long id){
        if(!unitRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found.");
        }
        unitRepository.deleteById(id);
    }
}
