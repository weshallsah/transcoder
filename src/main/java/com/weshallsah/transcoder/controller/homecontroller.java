package com.weshallsah.transcoder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class homecontroller {

    @GetMapping("/")
    public ResponseEntity<?> greet(){
        return new ResponseEntity<> ("Hello world",HttpStatus.OK);
    }
}
