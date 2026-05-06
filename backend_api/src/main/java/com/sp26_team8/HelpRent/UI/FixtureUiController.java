package com.sp26_team8.HelpRent.UI;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sp26_team8.HelpRent.entity.Fixture;
import com.sp26_team8.HelpRent.entity.Property;
import com.sp26_team8.HelpRent.entity.User;
import com.sp26_team8.HelpRent.service.FixtureService;
import com.sp26_team8.HelpRent.service.PropertyService;
import com.sp26_team8.HelpRent.service.UserService;

@Controller
@RequestMapping("/ui/fixtures")
public class FixtureUiController {
    private final FixtureService fixtureService;
    private final UserService userService;
    private final PropertyService propertyService;

    public FixtureUiController(FixtureService fixtureService, UserService userService, PropertyService propertyService){
        this.fixtureService = fixtureService;
        this.userService = userService;
        this.propertyService = propertyService;
    }

    //==============View all Fixtures=================//
    @GetMapping("")
    public String viewAllFixtures(Authentication auth, Model model){
        User user = userService.getUserByEmail(auth.getName());
        Property property = propertyService.getPropertyByLandlord(user.getUserId());
        
        model.addAttribute("role", user.getRole().name());
        model.addAttribute("fixtures", property.getFixtures());

        return "fixture/all";
    }

    //===================Creating New Fixture=======================//
    @GetMapping("/new")
    public String newFixtureForm(Authentication auth, Model model){
        User user = userService.getUserByEmail(auth.getName());
        
        model.addAttribute("role", user.getRole().name());

        return "fixture/form";
    }

    @PostMapping("/new")
    public String createFixture(Authentication auth, @RequestParam String title, @RequestParam String description){
        User user = userService.getUserByEmail(auth.getName());
        Property property = propertyService.getPropertyByLandlord(user.getUserId());

        Fixture fixture = new Fixture();
        fixture.setTitle(title);
        fixture.setDescription(description);
        fixture.setProperty(property);
        Fixture newfixture = fixtureService.createFixture(fixture, user.getUserId());

        // propertyService.addFixtureToProperty(property.getPropertyId(), newfixture.getFixtureId(), user.getUserId());

        return "redirect:/ui/fixtures/" + newfixture.getFixtureId();
    }

    //=================Fixture Details====================//
    @GetMapping("/{fixtureId}")
    public String viewFixture(Authentication auth, Model model, @PathVariable Long fixtureId){
        User user = userService.getUserByEmail(auth.getName());
        Fixture fixture = fixtureService.getFixtureById(fixtureId);

        model.addAttribute("role", user.getRole().name());
        model.addAttribute("fixture", fixture);
        
        return "fixture/view";
    }

    //=================Fixture Updtates====================//
    @GetMapping("/{fixtureId}/edit")
    public String updateFixtureForm(Authentication auth, Model model, @PathVariable Long fixtureId){
        User user = userService.getUserByEmail(auth.getName());

        model.addAttribute("role", user.getRole().name());
        model.addAttribute("fixture", fixtureService.getFixtureById(fixtureId));

        return "fixture/form";
    }

    @PostMapping("/{fixtureId}/edit")
    public String updateFixture(Authentication auth, Model model, @PathVariable Long fixtureId, @RequestParam String title, @RequestParam String description){
        User user = userService.getUserByEmail(auth.getName());
        
        Fixture fixture = new Fixture();
        fixture.setTitle(title);
        fixture.setDescription(description);

        fixtureService.updateFixture(fixtureId, fixture, user.getUserId());

        return "redirect:/ui/fixtures/" + fixtureId;
    }

    @PostMapping("/{fixtureId}/delete")
    public String deleteString(Authentication auth, @PathVariable Long fixtureId){
        User user = userService.getUserByEmail(auth.getName());

        Property property = propertyService.getPropertyByLandlord(user.getUserId());
        Fixture fixture = fixtureService.getFixtureById(fixtureId);
        propertyService.removeFixtureFromProperty(property.getPropertyId(), fixture, user.getUserId());

        //fixtureService.deleteFixture(fixtureId, user.getUserId());
        
        return "redirect:/ui/fixtures";
    }
}
