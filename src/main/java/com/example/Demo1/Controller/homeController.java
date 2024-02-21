package com.example.Demo1.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class homeController {
    @GetMapping("/firstapi")
    public String firstapi(){
        return "authonticated";
    }
}
