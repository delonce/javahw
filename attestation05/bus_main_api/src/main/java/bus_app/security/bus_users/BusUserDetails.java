package bus_app.security.bus_users;

import bus_app.models.BusUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Класс данных о пользователе, его имени, пароле и уровнях доступа
 */
@RequiredArgsConstructor
public class BusUserDetails implements UserDetails {

    /**
     * Объект, содержащий информацию о пользователе
     */
    private final BusUser busUser;

    /**
     * Метод, возвращающий список уровней доступа пользователя
     * @return список уровней доступа пользователя
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return busUser.getRoles().stream()
                .map(elem -> new SimpleGrantedAuthority("ROLE_" + elem))
                .toList();
    }

    /**
     * Метод, возвращающий пароль пользователя
     * @return пароль пользователя
     */
    @Override
    public String getPassword() {
        return busUser.getPassword();
    }

    /**
     * Метод, возвращающий имя пользователя
     * @return имя пользователя
     */
    @Override
    public String getUsername() {
        return busUser.getUsername();
    }
}