package app.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * Класс web - контроллреа, предоставляющий интерфейс для получения информации о пользователе
 * @author Danma
 */
@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    /**
     * Процедура получения уровня доступа к данным
     * @param authentication информация об аунтификации пользователя
     * @return уровень доступа пользователя в формате строки
     */
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

    /**
     * Процедура получения имени текущего пользователя
     * @param authentication информация об аунтификации пользователя
     * @return словарь, содержащий имя пользователя
     */
    @GetMapping("/info")
    public Map<String, String> getInfoByPrincipal(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal()
                instanceof DefaultOidcUser user) {

            return Collections.singletonMap("username",user.getEmail());
        }

        return Collections.singletonMap("username","anonymous");
    }
}