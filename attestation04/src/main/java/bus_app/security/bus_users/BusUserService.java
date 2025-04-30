package bus_app.security.bus_users;

import bus_app.models.BusUser;
import bus_app.repositories.BusUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Класс, предоставляющий информацию о пользователе в формате, требуемом для проверки его прав доступа
 */
@Service
@RequiredArgsConstructor
public class BusUserService implements UserDetailsService {

    /**
     * Экземпляр интерфейса доступа к данным о пользователях в БД
     */
    private final BusUserRepository repository;

    /**
     * Метод получения данных о пользователе по его имени
     * @param username имя пользователя
     * @return информация о пользователе
     * @throws UsernameNotFoundException отсутствие пользователя с таким именем в БД
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<BusUser> student = repository.findById(username);

        return student.map(BusUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Incorrect name: " + username));
    }
}