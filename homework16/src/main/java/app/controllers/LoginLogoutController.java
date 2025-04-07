package app.controllers;

import app.models.dto.UserLoginDto;
import app.security.CustomUserDetailsService;
import app.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginLogoutController {

    private final AuthenticationManager manager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userDto) {
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