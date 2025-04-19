package bus_app.security.bus_users;

import bus_app.models.BusUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class BusUserDetails implements UserDetails {

    private final BusUser busUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return busUser.getRoles().stream()
                .map(elem -> new SimpleGrantedAuthority("ROLE_" + elem))
                .toList();
    }

    @Override
    public String getPassword() {
        return busUser.getPassword();
    }

    @Override
    public String getUsername() {
        return busUser.getUsername();
    }
}