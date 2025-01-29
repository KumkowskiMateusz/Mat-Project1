package com.revature.Project1.Components;

import com.revature.Project1.Services.DuckService;
import com.revature.Project1.Services.UserService;
import com.revature.Project1.Services.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final UserService userService;
    private final DuckService duckService;
    private final SupplementaryFunctions supFunctions;

    @Autowired
    public ScheduledTasks(UserService userService, DuckService duckService, WorldService worldService, SupplementaryFunctions supFunctions) {
        this.userService = userService;
        this.duckService = duckService;
        this.supFunctions = supFunctions;
    }

//    @Scheduled(fixedRate = 5000)
//    private void increaseBankAccounts(){
//        List<User> allUsers = userService.getAllUsers();
//        for(User user : allUsers){
//            List<Duck> ducks = duckService.getDucksByForeignId(user.getId());
//            Double totalAmountIncrease = supFunctions.getTotalAmount(ducks,1);
//            try{
//                userService.setUserBankAccount(user,totalAmountIncrease);
//            } catch (Exception e) {
//                System.out.println("Bank Not Updated");
//            }
//
//        }
//
//    }
}
