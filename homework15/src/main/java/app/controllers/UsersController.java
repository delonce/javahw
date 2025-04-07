package app.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @GetMapping("/role")
    public String getRole(Authentication authentication) {
        if (authentication == null) {
            return "NONE";
        }

        return authentication.getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority();
    }

    @GetMapping("/info")
    public Map<String, String> getInfoByPrincipal(Principal principal) {
        if(principal==null){
            return Collections.singletonMap("username","anonymous");
        }

        return Collections.singletonMap("username",principal.getName());
    }
}