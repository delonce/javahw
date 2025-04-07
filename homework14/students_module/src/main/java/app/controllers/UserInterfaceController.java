package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserInterfaceController {

    @GetMapping("/profile")
    public String getProfilePage() {
        return "profile";
    }
}