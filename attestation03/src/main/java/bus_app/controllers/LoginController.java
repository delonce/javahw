package bus_app.controllers;

import bus_app.dto.bus_users.BusUserDto;
import bus_app.security.bus_users.BusUserService;
import bus_app.security.jwt.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Авторизация", description = "API для получения авторизационного токена")
public class LoginController {

    private final AuthenticationManager manager;
    private final BusUserService userDetailsService;
    private final JwtService jwtService;

    @Operation(
            summary = "Получить JWT - токен",
            description = "Возвращает токен в случае успешной авторизации",
            tags = {"Авторизация"}
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Parameter(description = "Информация об аутентификационных данных пользователя")
            @RequestBody BusUserDto userDto) {
        try{
            Authentication auth = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(),userDto.getPassword())
            );

            UserDetails user = userDetailsService.loadUserByUsername(userDto.getUsername());
            return ResponseEntity.ok(jwtService.generateToken(user));
        } catch (AuthenticationException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}