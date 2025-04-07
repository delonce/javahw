package app.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @GetMapping("/role")
    public String getRole(Authentication authentication) {
        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            return "Anonymous";
        }

        System.out.println(authentication.getAuthorities());

        return authentication.getAuthorities()
                .stream().toList()
                .get(1)
                .getAuthority();
    }

    @GetMapping("/info")
    public Map<String, String> getInfoByPrincipal(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal()
                instanceof DefaultOidcUser user) {

            return Collections.singletonMap("username",user.getEmail());
        }

        return Collections.singletonMap("username","anonymous");
    }
}