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
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Класс конфигурации цепочки безопасности доступа к защищенным ресурсам приложения
 * @author Danma
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Процедура создания бина кодировщика паролей пользователей
     * @return кодировщик формата bcrypt
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 15);
    }

    /**
     * Процедура создания списка параметров CORS - защиты ресурсов web - приложения
     * @return список CORS - параметров
     */
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Процедура конфигурации цепочки безопасности
     * @param http объект - builder цепочки безопасности
     * @param oidUserService сервис доступа к авторизационным данным OAuth2
     * @return цепочка фильтров безопасности
     * @throws Exception любые ошибки, возникающие в процессе конфигурации
     */
    @Bean
    public SecurityFilterChain configSecurityChain(HttpSecurity http,
                                                   CustomOidUserService oidUserService) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/users**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/tasks").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/tasks").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tasks**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).logout(logout->logout
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/tasks")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .addLogoutHandler(((request, response, authentication) ->
                                new SecurityContextLogoutHandler().logout(request, response, authentication)))
                ).oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(point ->
                                point.oidcUserService(oidUserService))
                        .defaultSuccessUrl("/tasks",true));

        return http.build();
    }
}