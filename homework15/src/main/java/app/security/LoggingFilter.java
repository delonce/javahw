package app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class.getName());
    private static final Base64.Decoder decoder = Base64.getUrlDecoder();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authData = request.getHeader("Authorization");

        LOGGER.info(String.format(
                "Request: %s %s; Authorization header: %s",
                request.getMethod(),
                request.getRequestURI(),
                (authData == null) ? ("-") : (new String(decoder.decode(authData
                        .replace("Basic ", "").getBytes(StandardCharsets.UTF_8))))
        ));

        filterChain.doFilter(request, response);
    }
}