package com.benchikh.demo.controllers;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
public class ManagementController {


    @GetMapping
    public String get() {
        return "GET:: Manager Controller";
    }

    @PostMapping
    public String post() {
        return "POST:: Manager Controller";
    }

    @PutMapping
    public String put() {
        return "PUT:: Manager Controller";
    }

    @DeleteMapping
    public String delete() {
        return "Manager:: Admin Controller";
    }
}
