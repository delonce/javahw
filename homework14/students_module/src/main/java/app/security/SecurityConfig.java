package app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 15);
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers("/custom-login", "/students/comments/grade**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/students").permitAll()
                        .anyRequest().authenticated()
                ).formLogin(form -> form
                        .loginPage("/custom-login.html")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/profile",true)
                        .failureUrl("/custom-login.html?error=true")
                        .permitAll()
                )
                .logout(logout->logout
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/custom-login?logout=true")
                        .permitAll()
                );

        return http.build();
    }
}