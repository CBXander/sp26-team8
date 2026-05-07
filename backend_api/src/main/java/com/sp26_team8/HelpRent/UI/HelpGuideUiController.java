package com.sp26_team8.HelpRent.UI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import org.springframework.web.multipart.MultipartFile;

import com.sp26_team8.HelpRent.entity.*;
import com.sp26_team8.HelpRent.service.*;

@Controller
@RequestMapping("/helpGuides")
public class HelpGuideUiController {
    private final HelpGuideService helpGuideService;
    private final UserService userService;
    private final FixtureService fixtureService;
    private final PropertyService propertyService;
    private final UnitService unitService;
    public HelpGuideUiController(HelpGuideService helpGuideService, UserService userService,
                                FixtureService fixtureService, PropertyService propertyService, UnitService unitService){
        this.helpGuideService=helpGuideService;
        this.userService=userService;
        this.fixtureService=fixtureService;
        this.propertyService=propertyService;
        this.unitService=unitService;
    }

    //==================View HelpGuides=====================//
    @GetMapping("")
    public String viewAllHelpGuides(Authentication auth, Model model){
        User user = userService.getUserByEmail(auth.getName());
        model.addAttribute("role", user.getRole().name());

        switch (user.getRole()) {
            case LANDLORD:
                Property property = propertyService.getPropertyByLandlord(user.getUserId());
                
                model.addAttribute("helpGuides", property.getHelpGuides());
                break;
        
            case MAINTENANCE:
                break;

            case TENANT:
                Unit unit = unitService.getUnitByTenant(user.getUserId());

                model.addAttribute("fixtures", unit.getFixtures());
                break;
        }
        
        
        return "helpGuide/all";
    }

    @GetMapping("/{helpGuideId}")
    public String viewHelpGuide(Authentication auth, @PathVariable Long helpGuideId, Model model){
        User user = userService.getUserByEmail(auth.getName());
        HelpGuide guide = helpGuideService.getHelpGuideById(helpGuideId);

        model.addAttribute("role", user.getRole().name());
        model.addAttribute("guide", guide);

        return "helpGuide/view";
    }

    //==============New HelpGuide Form==================//
    @GetMapping("/new")
    public String newHelpGuideForm(Authentication auth, Model model){
        User user = userService.getUserByEmail(auth.getName());
        Property property = propertyService.getPropertyByLandlord(user.getUserId());
        
        model.addAttribute("role", user.getRole().name());
        model.addAttribute("fixtures", property.getFixtures());

        return "helpGuide/form";
    }
    
    @PostMapping("/new")
    public String createHelpGuide(Authentication auth, @RequestParam String title, 
                                    @RequestParam String description, @RequestParam(required = false) String category, 
                                    @RequestParam(required = false) Long fixtureId, @RequestParam(required = false) MultipartFile file) 
                                    throws IOException {
        User user = userService.getUserByEmail(auth.getName());
        Property property = propertyService.getPropertyByLandlord(user.getUserId());

        HelpGuide newGuide = new HelpGuide();
        newGuide.setTitle(title);
        newGuide.setDescription(description);
        newGuide.setCategory(category);
        newGuide.setProperty(property);

        if (file != null && !file.isEmpty()) {
            String uploadDir = "uploads/helpGuides/";
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            newGuide.setFilePath(fileName);
        }
        
        HelpGuide saved = helpGuideService.createHelpGuide(newGuide, fixtureId, user.getUserId());
        
        return "redirect:/helpGuides/" + saved.getHelpGuideId();
    }

    //==================== Edit Existing HelpGuide =================//
    @GetMapping("/{helpGuideId}/edit")
    public String editHelpGuideForm(Authentication auth, @PathVariable Long helpGuideId, Model model){
        User user = userService.getUserByEmail(auth.getName());
        HelpGuide guide = helpGuideService.getHelpGuideById(helpGuideId);
        Property property = propertyService.getPropertyByLandlord(user.getUserId());

        model.addAttribute("role", user.getRole().name());
        model.addAttribute("guide", guide);
        model.addAttribute("fixtures", property.getFixtures());

        return "helpGuide/form";
    }

    @PostMapping("/{helpGuideId}/edit")
    public String editHelpGuide(Authentication auth, @PathVariable Long helpGuideId,
                                @RequestParam String title, @RequestParam String description, @RequestParam String category,
                                @RequestParam Long fixtureId){
        User user = userService.getUserByEmail(auth.getName());
        HelpGuide guide = helpGuideService.getHelpGuideById(helpGuideId);

        guide.setTitle(title);
        guide.setDescription(description);
        guide.setCategory(category);

        HelpGuide saved = helpGuideService.createHelpGuide(guide, fixtureId, user.getUserId());

        return "redirect:/helpGuides/" + saved.getHelpGuideId();
    }

    @PostMapping("/{helpGuideId}/delete")
    public String deleteHelpGuide(Authentication auth, @PathVariable Long helpGuideId){
        User user = userService.getUserByEmail(auth.getName());
        helpGuideService.deleteHelpGuide(helpGuideId, user.getUserId());

        return "redirect:/helpGuides";
    }
    
}
