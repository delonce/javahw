package bus_app.security;

import bus_app.security.bus_users.BusUserService;
import bus_app.security.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Класс конфигурации уровней доступа к API приложения
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Метод создания бина кодировщика паролей
     * @return экземпляр кодировщика паролей для БД
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 15);
    }

    /**
     * Метод создания бина сервиса аутентификации с заданными инструментами доступа к данным о пользоателях
     * @param service экземпляр класс с API для получения информации о пользователе и его уровнях доступа
     * @param encoder экземпляр кодировщика паролей
     * @return экземпляр сервиса аутентификации пользователя
     */
    @Bean
    public AuthenticationProvider authenticationProvider(BusUserService service, PasswordEncoder encoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(service);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }

    /**
     * Метод создания бина менеджера аутентификации
     * @param config экземпляр класса конфигурации параметров аутентификации
     * @return экземпляр менеджера аутентификации
     * @throws Exception ошибка при получении экземпляра менеджера аутентификации
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Метод, предоставляющий экземпляр конфигурации CORS - защиты с параметрами свободного доступа
     * @return экземпляр конфигурации CORS - защиты
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
     * Метод создания бина класса цепочки фильтров доступа к API
     * @param http объект класса, предоставляющего инструменты для настройки цепочки защиты API
     * @param provider экземпляр сервиса аутентификации пользователя
     * @param filter средство валидации JWT - токенов
     * @return экземпляр цепочки защиты API
     * @throws Exception ошибка формирования цепочки защиты
     */
    @Bean
    public SecurityFilterChain configSecurityChain(HttpSecurity http,
                                                   AuthenticationProvider provider,
                                                   JwtFilter filter) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
                        .requestMatchers("/v3/**", "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(provider)
                .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }
}