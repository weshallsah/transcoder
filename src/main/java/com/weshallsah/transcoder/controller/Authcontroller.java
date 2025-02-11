package com.weshallsah.transcoder.controller;


import com.weshallsah.transcoder.model.Users;
import com.weshallsah.transcoder.services.userServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Authcontroller {

    @Autowired
    private userServices services;

    @PostMapping("/register")
    public Users register(@RequestBody Users users){
        return services.register(users);
    }

    @PostMapping("/login")
    public String Login(@RequestBody Users users){
        return services.verify(users);
    }
    
}
