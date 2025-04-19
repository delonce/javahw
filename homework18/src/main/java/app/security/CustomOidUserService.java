package app.security;

import app.models.User;
import app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Класс - сервис для получения информации о пользователе для OAuth2 авторизации
 * @author Danma
 */
@Component
@RequiredArgsConstructor
public class CustomOidUserService extends OidcUserService {

    /** Репозиторий, выполняющий запросы в базу данных */
    private final UserRepository repository;

    /**
     * Процедура получения информации о пользователе и его правах доступа
     * @param request токен пользовательской сессии
     * @return информация о текущем пользователе
     */
    @Override
    public OidcUser loadUser(OidcUserRequest request) {
        OidcUser user = super.loadUser(request);

        Optional<User> dbUser = repository.findByUsername(user.getEmail());
        if (dbUser.isEmpty()) {
            return new DefaultOidcUser(
                    null,
                    request.getIdToken(),
                    user.getUserInfo(),
                    "email"
            );
        }

        Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());
        for (String role: dbUser.get().getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return new DefaultOidcUser(
                authorities,
                request.getIdToken(),
                user.getUserInfo(),
                "email"
        );
    }
}