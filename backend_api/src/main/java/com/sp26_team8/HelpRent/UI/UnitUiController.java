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
import com.sp26_team8.HelpRent.entity.*;

@Controller
@RequestMapping("/ui/units")
public class UnitUiController {
    private final UnitService unitService;
    private final UserService userService;
    private final PropertyService propertyService;

    public UnitUiController(UnitService unitService, UserService userService, PropertyService propertyService){
        this.unitService = unitService;
        this.userService = userService;
        this.propertyService = propertyService;
    }

    //======= Unit Details =========//
    @GetMapping("/{unitId}")
    public String viewUnit(Authentication auth, Model model, @PathVariable Long unitId){
        User user = userService.getUserByEmail(auth.getName());
        Unit unit = unitService.verifyLandlordUnitOwnership(user.getUserId(), unitId);

        model.addAttribute("role", user.getRole().name());
        model.addAttribute("unit", unit);
        
        return "unit/viewUnit";
    }

    @PostMapping("/{unitId}/maintenance")
    public String setMaintenance(Authentication auth, @PathVariable Long unitId){
        User user = userService.getUserByEmail(auth.getName());
        unitService.verifyLandlordUnitOwnership(user.getUserId(), unitId);
        unitService.setUnitUnderMaintenace(unitId, user.getUserId());

        return "redirect:/ui/units/" + unitId;
    }
    
    //======= Tennant Handling ========//
    @GetMapping("/{unitId}/tenant/new")
    public String newTenant(Authentication auth, @PathVariable Long unitId, Model model){
        User user = userService.getUserByEmail(auth.getName());
        unitService.verifyLandlordUnitOwnership(user.getUserId(), unitId);
        model.addAttribute("unitId", unitId);
        model.addAttribute("role", user.getRole().name());

        return "unit/newTenantForm";
    }

    @PostMapping("/{unitId}/tenant/new")
    public String createTenant(Authentication auth, @PathVariable Long unitId,@RequestParam String email,
                            @RequestParam String firstName, @RequestParam String lastName, @RequestParam String password){
        User user = userService.getUserByEmail(auth.getName());
        unitService.verifyLandlordUnitOwnership(user.getUserId(), unitId);
        
        User tenant = new User();
        tenant.setEmail(email);
        tenant.setPasswordHash(password);
        tenant.setFirstName(firstName);
        tenant.setLastName(lastName);
        tenant.setRole(UserRole.TENANT);
        tenant.setStatus(UserStatus.ACTIVE);
        
        User created = userService.createUser(tenant);

        unitService.addTenantToUnit(unitId, created.getUserId(), user.getUserId());

        return "redirect:/ui/units/" + unitId;
    }

    @PostMapping("/{unitId}/tenant/remove")
    public String removeTenant(Authentication auth, @PathVariable Long unitId, Model model){
        User user = userService.getUserByEmail(auth.getName());
        unitService.verifyLandlordUnitOwnership(user.getUserId(), unitId);

        unitService.removeTenantFromUnit(unitId, user.getUserId());

        return "redirect:/ui/units/" + unitId;
    }

}
