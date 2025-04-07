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

@Component
@RequiredArgsConstructor
public class CustomOidUserService extends OidcUserService {

    private final UserRepository repository;

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