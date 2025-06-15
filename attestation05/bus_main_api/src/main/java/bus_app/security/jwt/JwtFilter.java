package bus_app.security.jwt;

import bus_app.security.bus_users.BusUserService;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

/**
 * Класс фильтра / средства валидации JWT - токенов
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    /**
     * Экземпляр класса, предоставляющего инструменты для проверки валидности токена и генерации нового
     */
    private final JwtService jwtService;

    /**
     * Экземпляр класса, предоставляющего доступ к информации о пользователях и уровнях доступа
     */
    private final BusUserService busUserService;

    /**
     *
     * @param request информация о запросе пользователя
     * @param response информация об ответе пользователю
     * @param filterChain цепь фильтров защиты API
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = "";

        Cookie[] cookies = request.getCookies();
        boolean tokenFound = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwtToken")) {
                    authHeader = cookie.getValue();
                    tokenFound = true;
                    break;
                }
            }
        }

        String headerWithToken = request.getHeader("Authorization");

        if (!tokenFound && headerWithToken != null && headerWithToken.startsWith("Bearer ")) {
            authHeader = headerWithToken.substring(7);
            tokenFound = true;
        }

        if (!tokenFound) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String username = jwtService.extractUsername(authHeader);

            if (!StringUtils.isEmpty(username)) {
                UserDetails details = busUserService.loadUserByUsername(username);

                if (jwtService.isTokenValid(authHeader, details)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            details.getUsername(), details.getPassword(),
                            details.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (SignatureException ignored) {}

        filterChain.doFilter(request, response);
    }
}