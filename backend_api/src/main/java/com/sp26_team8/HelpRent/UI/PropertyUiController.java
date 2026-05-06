package com.sp26_team8.HelpRent.UI;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sp26_team8.HelpRent.service.*;
import com.sp26_team8.HelpRent.entity.*;;



@Controller
@RequestMapping("/ui/properties")
public class PropertyUiController {
    private final PropertyService propertyService;
    private final UserService userService;
    private final UnitService unitService;

    public PropertyUiController(PropertyService propertyService, UserService userService, UnitService unitService){
        this.propertyService = propertyService;
        this.userService = userService;
        this.unitService = unitService;
    }

    //======== New Property Methods ===========//
    @GetMapping("/new")
    public String newProperty(Authentication auth, Model model){
        User user = userService.getUserByEmail(auth.getName());
        model.addAttribute("role", user.getRole().name());

        return "property/propertyForm";
    } 

    @PostMapping("/new")
    public String createProperty(Authentication auth, @RequestParam String name, @RequestParam String address) {
        User user = userService.getUserByEmail(auth.getName());
        
        Property property = new Property();
        property.setName(name);
        property.setAddress(address);
        
        Property created = propertyService.createProperty(property, user.getUserId());

        return "redirect:/ui/properties/" + created.getPropertyId();
    }

    //======== New Staff Methods ===========//
    @GetMapping("/{propertyId}/staff/new")
    public String newStaff(Authentication auth, @PathVariable Long propertyId, Model model) {
        User user = userService.getUserByEmail(auth.getName());
        propertyService.verifyLandlordOwnership(propertyId, user.getUserId());
        model.addAttribute("propertyId", propertyId);
        model.addAttribute("role", user.getRole().name());

        return "property/newStaffForm";
    }

    @PostMapping("/{propertyId}/staff/new")
    public String createStaff(Authentication auth, @RequestParam String email, @PathVariable Long propertyId, 
                                @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName){
        User landlord = userService.getUserByEmail(auth.getName());

        User staff = new User();
        staff.setEmail(email);
        staff.setPasswordHash(password);
        staff.setFirstName(firstName);
        staff.setLastName(lastName);
        staff.setRole(UserRole.MAINTENANCE);
        staff.setStatus(UserStatus.ACTIVE);

        User created = userService.createUser(staff);
        propertyService.addStaffToProperty(propertyId, created.getUserId(), landlord.getUserId());

        return "redirect:/ui/properties/" + propertyId;
    }
    //======== Remove Staff ===========//
    @PostMapping("/{propertyId}/staff/{staffId}/remove")
    public String deleteStaff(Authentication auth, @PathVariable Long propertyId, @PathVariable Long staffId){
        User user = userService.getUserByEmail(auth.getName());
        propertyService.verifyLandlordOwnership(propertyId, user.getUserId());

        propertyService.removeStaffFromProperty(propertyId, staffId, user.getUserId());

        return "redirect:/ui/properties/" + propertyId;
    }

    //======== New Unit Methods ===========//
    @GetMapping("/{propertyId}/units/new")
    public String newUnit(Authentication auth, @PathVariable Long propertyId, Model model){
        User user = userService.getUserByEmail(auth.getName());
        propertyService.verifyLandlordOwnership(propertyId, user.getUserId());
        
        model.addAttribute("propertyId", propertyId);
        model.addAttribute("role", user.getRole().name());
        
        return "property/newUnitForm";
    }

    @PostMapping("/{propertyId}/units/new")
    public String createUnit(Authentication auth, @PathVariable Long propertyId, 
                                @RequestParam(required = false) String unitNumber, @RequestParam(required = false) String unitAddress) {
        User landlord = userService.getUserByEmail(auth.getName());

        Unit unit = new Unit();
        unit.setUnitNum(unitNumber);
        unit.setUnitAddress(unitAddress);
        unit.setUnitStatus(UnitStatus.VACANT);

        unitService.createUnit(unit,landlord.getUserId(),propertyId);
        return "redirect:/ui/properties/" + propertyId;
    }
    //======== Remove Unit ===========//
    @PostMapping("/{propertyId}/units/{unitId}/remove")
    public String removeUnit(Authentication auth, @PathVariable Long propertyId, @PathVariable Long unitId){
        User user = userService.getUserByEmail(auth.getName());
        propertyService.verifyLandlordOwnership(propertyId, user.getUserId());

        unitService.deleteUnit(unitId, user.getUserId());
        return "redirect:/ui/properties/" + propertyId;
    }
    //======== View Property ===========//
    @GetMapping("/{propertyId}")
    public String viewProperty(Authentication auth, @PathVariable Long propertyId, Model model){
        User user = userService.getUserByEmail(auth.getName());
        Property property = propertyService.verifyLandlordOwnership(propertyId, user.getUserId());
    
        model.addAttribute("property", property);
        model.addAttribute("role", user.getRole().name());
        model.addAttribute("user", user);
        return "property/viewProperty";
    }
}
