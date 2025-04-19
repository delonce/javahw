package app.security;

import app.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Класс для хранения информации о пользователе и предоставляющий удобный доступ
 * к его правам, паролю и имени
 * @author Danma
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    /** Поле с информацией о пользователе */
    private final User user;

    /**
     * Процедура получения списка прав доступа пользователя
     * @return список прав доступа пользователя
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(elem -> new SimpleGrantedAuthority("ROLE_" + elem))
                .toList();
    }

    /**
     * Процедура получения пароля пользователя
     * @return пароль пользователя
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Процедура получения имени пользователя
     * @return имя пользователя
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }
}