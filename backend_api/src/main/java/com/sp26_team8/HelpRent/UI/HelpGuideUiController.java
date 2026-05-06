package com.sp26_team8.HelpRent.UI;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sp26_team8.HelpRent.entity.HelpGuide;
import com.sp26_team8.HelpRent.entity.Property;
import com.sp26_team8.HelpRent.entity.User;
import com.sp26_team8.HelpRent.service.FixtureService;
import com.sp26_team8.HelpRent.service.HelpGuideService;
import com.sp26_team8.HelpRent.service.PropertyService;
import com.sp26_team8.HelpRent.service.UserService;

@Controller
@RequestMapping("/ui/helpGuides")
public class HelpGuideUiController {
    private final HelpGuideService helpGuideService;
    private final UserService userService;
    private final FixtureService fixtureService;
    private final PropertyService propertyService;

    public HelpGuideUiController(HelpGuideService helpGuideService, UserService userService,
                                FixtureService fixtureService, PropertyService propertyService){
        this.helpGuideService=helpGuideService;
        this.userService=userService;
        this.fixtureService=fixtureService;
        this.propertyService=propertyService;
    }

    //==================View All HelpGuides=====================//
    @GetMapping("")
    public String viewAllHelpGuides(Authentication auth, Model model){
        User user = userService.getUserByEmail(auth.getName());
        Property property = propertyService.getPropertyByLandlord(user.getUserId());
        
        model.addAttribute("role", user.getRole().name());
        model.addAttribute("helpGuides", property.getHelpGuides());
        
        return "helpGuide/all";
    }
    //==============New HelpGuide Form==================//
    @GetMapping("/new")
    public String newHelpGuideForm(Authentication auth, Model model){
        User user = userService.getUserByEmail(auth.getName());
        
        model.addAttribute("role", user.getRole().name());
        
        return "helpGuide/form";
    }

    @PostMapping("/new")
    public String createHelpGuide(Authentication auth, @RequestParam String title, 
                                    @RequestParam String description, @RequestParam String category, 
                                    @RequestParam Long fixtureId){
        User user = userService.getUserByEmail(auth.getName());
        Property property = propertyService.getPropertyByLandlord(user.getUserId());

        HelpGuide newGuide = new HelpGuide();
        newGuide.setTitle(title);
        newGuide.setDescription(description);
        newGuide.setCategory(category);

        HelpGuide saved = helpGuideService.createHelpGuide(newGuide, fixtureId, user.getUserId());
        
        property.getHelpGuides().add(newGuide);
        
        return "redirect: /ui/helpGuides/" + saved.getHelpGuideId();
    }

}
