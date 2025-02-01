package org.delonce.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/greeting")
    public String demoController(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {
        return "Привет, " + name + "!";
    }
}