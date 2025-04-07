package app.security.filters;

import app.security.CustomUserDetailsService;
import app.security.jwt.JwtService;
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
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtFilter.class.getName());
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (StringUtils.isEmpty(authHeader)) {
            Cookie[] cookies = request.getCookies();
            boolean cookieFound = false;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("jwtToken")) {
                        authHeader = cookie.getValue();
                        cookieFound = true;
                        break;
                    }
                }
            }

            if (!cookieFound) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        else {
            if (authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }
            else {
                filterChain.doFilter(request, response);
                return;
            }
        }

        LOGGER.info("Found auth data: " + authHeader);

        if (StringUtils.isEmpty(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtService.extractUsername(authHeader);

        if (!StringUtils.isEmpty(username)) {
            UserDetails details = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(authHeader, details)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        details.getUsername(), details.getPassword(),
                        details.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}