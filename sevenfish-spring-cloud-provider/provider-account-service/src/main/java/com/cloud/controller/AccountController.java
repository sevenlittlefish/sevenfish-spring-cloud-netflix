package com.cloud.controller;

import com.cloud.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("changeMoney")
    public void changeMoney(String userId,Integer money){
        accountService.changeMoney(userId, money);
    }
}
