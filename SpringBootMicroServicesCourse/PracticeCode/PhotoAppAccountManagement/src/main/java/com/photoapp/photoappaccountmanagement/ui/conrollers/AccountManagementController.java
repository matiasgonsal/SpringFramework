package com.photoapp.photoappaccountmanagement.ui.conrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class AccountManagementController {

    @GetMapping("/status/check")
    public String healthCheck (){
        return "This instance of the AccountManagement Microservice is working properly.";
    }
}
