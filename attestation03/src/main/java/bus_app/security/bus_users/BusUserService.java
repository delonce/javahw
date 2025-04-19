package bus_app.security.bus_users;

import bus_app.models.BusUser;
import bus_app.repositories.BusUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusUserService implements UserDetailsService {

    private final BusUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<BusUser> student = repository.findById(username);

        return student.map(BusUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Incorrect name: " + username));
    }
}