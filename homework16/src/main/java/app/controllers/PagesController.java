package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/tasks")
    public String getProfilePage() {
        return "tasks";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
}