package com.angelfym.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String getResponse() {
        return "Welcome to v1 Student API";
    }
}
