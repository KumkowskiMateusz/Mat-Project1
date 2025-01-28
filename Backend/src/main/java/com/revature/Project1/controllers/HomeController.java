package com.revature.Project1.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.revature.Project1.exceptions.NotFound;
import com.revature.Project1.models.WorldDuck;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.Project1.Components.SupplementaryFunctions;
import com.revature.Project1.exceptions.ClientSideException;
import com.revature.Project1.models.Duck;
import com.revature.Project1.models.User;
import com.revature.Project1.models.World;
import com.revature.Project1.services.DuckService;
import com.revature.Project1.services.UserService;
import com.revature.Project1.services.WorldService;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
@RequestMapping("backpack")
@EnableAsync
public class HomeController {
    private final UserService userService;
    private final DuckService duckService;
    private final WorldService worldService;
    private final SupplementaryFunctions supFunctions;

    @Autowired
    public HomeController(UserService userService, DuckService duckService, WorldService worldService, SupplementaryFunctions supFunctions) {
        this.userService = userService;
        this.duckService = duckService;
        this.worldService = worldService;
        this.supFunctions = supFunctions;
    }

    @GetMapping(value = "")
    public ResponseEntity getUserInfoById(@CookieValue(value = "project1LoginCookie", defaultValue = "none") String cookie){

        try{
            User resultUser = userService.getUserById(Integer.parseInt(cookie));
            return ResponseEntity.status(HttpStatus.OK).body(resultUser);
        }
        catch (NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Something went wrong");
        }
    }


    @PostMapping(value = "")
    public ResponseEntity postNewDuck(@AuthenticationPrincipal UserDetails userDetails){

        //OBTAINING WORLD DATA
        World world = null;
        try {
            world = worldService.getWorldValuesById(1);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Id");
        }

        //CHECKING BACKPACK SPACE
        User resultUser;
        try{
            resultUser = userService.getUserByUsername(userDetails.getUsername());
            if(resultUser.getInventory().getBackpackSpaceUsed() >= resultUser.getInventory().getBackpackSpace()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Backpack Full");

        }
        catch (NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Something Went Wrong");
        }



        // Calculating Duck Obtained
        Set<WorldDuck> duckAmounts = world.getWorldInfo();
        WorldDuck revievedDuck = worldService.getDuckRank(duckAmounts);




        Duck returnDuck;
        if(revievedDuck == null){
            returnDuck = new Duck(resultUser.getId(),"Trash","Spare Garbage",0);
        }
        else{
            returnDuck = new Duck(resultUser.getId(),revievedDuck.getRank(),revievedDuck.getRank() + " Duck",revievedDuck.getValue());
        }


        try{
            returnDuck = duckService.createDuck(returnDuck);
            Thread.sleep(3000);
            return ResponseEntity.status(HttpStatus.OK).body(returnDuck);
        }
        catch (Exception e){
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body("Something Went Wrong");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping(value = "")
    public ResponseEntity patchDuckNicknameById (@CookieValue(value = "project1LoginCookie", defaultValue = "none") String cookie, @RequestBody Duck duck) {
        if(cookie.equals("none")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        try {
            Duck resultDucks = duckService.setDuckNicknameById(duck);
            return ResponseEntity.status(HttpStatus.OK).body(resultDucks);
        } catch (ClientSideException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Improper Info");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Something Went wrong");
        }
    }

    @DeleteMapping(value = "")
    public ResponseEntity deleteDuckById (@CookieValue(value = "project1LoginCookie", defaultValue = "none") String cookie, @RequestBody Duck duck){
        if(cookie.equals("none")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        try {
            Optional<Duck> resultDuck = duckService.deleteDuckById(duck);
            if (resultDuck.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Improper Info");
            if(resultDuck.get().getReferenceId() != Integer.parseInt(cookie)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Improper Info");
            return ResponseEntity.status(HttpStatus.OK).body(resultDuck);
        }
        catch (ClientSideException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Improper Info");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Something Went wrong");
        }
    }

    @GetMapping(value = "ducks")
    public ResponseEntity getDucksOwnedById (@CookieValue(value = "project1LoginCookie", defaultValue = "none") String cookie){
        List<Duck> resultDucks;
        try{
            resultDucks = duckService.getDucksByForeignId(Integer.parseInt(cookie));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        return ResponseEntity.status(HttpStatus.OK).body(resultDucks);
    }

    @PreAuthorize("ADMIN")
    @PatchMapping(value = "ducks")
    public ResponseEntity patchSetAvailableDucks(@CookieValue(value = "project1LoginCookie", defaultValue = "none") String cookie,@RequestBody World world){
        if(cookie.equals("none")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        try {
            User resultUser = userService.getUserById(Integer.parseInt(cookie));
            World resultWorld = worldService.setWorldValues(world);
            return ResponseEntity.status(HttpStatus.OK).body(resultWorld);
        } catch (NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        catch (ClientSideException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Improper Info");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went wrong");
        }
    }

    @GetMapping(value = "world")
    public ResponseEntity getWorldInfo(@RequestBody World world){
        try {
            World gotWorld = worldService.getWorldValuesById(world.getId());
            return ResponseEntity.status(HttpStatus.OK).body(gotWorld);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Id");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "price")
    public ResponseEntity getBackpackPrice(@CookieValue(value = "project1LoginCookie", defaultValue = "none") String cookie){
        if(cookie.equals("none")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        try {
            User resultUser = userService.getUserById(Integer.parseInt(cookie));
            double backpackPrice = Math.pow(1.5,resultUser.getInventory().getBackpackSpace() - 5.0);
            backpackPrice = Math.round(backpackPrice * 100.0) / 100.0;
            return ResponseEntity.status(HttpStatus.OK).body(backpackPrice);
        }
        catch (NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping(value = "price")
    public ResponseEntity buyBackpack(@CookieValue(value = "project1LoginCookie", defaultValue = "none") String cookie,
                                      @AuthenticationPrincipal UserDetails userDetails){
        if(cookie.equals("none")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        try {
            User resultUser = userService.getUserByUsername(userDetails.getUsername());

            double backpackPrice = Math.pow(1.5,resultUser.getInventory().getBackpackSpace() - 5.0);
            backpackPrice = Math.round(backpackPrice * -100.0) / 100.0;
            if(backpackPrice * -1 > resultUser.getInventory().getBankAccount()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Too Expensive");
            User user = userService.setUserBankAccount(resultUser,backpackPrice);
            user = userService.setUserBackpackAmount(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch(NotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
}
