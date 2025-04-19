package app.security;

import app.models.User;
import app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Класс - сервис для получения информации об авторизационных данных пользователя
 * из базы данных
 * @author Danma
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /** Репозиторий для доступа к записям о пользователях */
    private final UserRepository repository;

    /**
     * Процедура получения информации о пользователе из базы данных
     * @param username имя пользователя
     * @return информация о пользователе (имя, пароль, права доступа)
     * @throws UsernameNotFoundException ошибка доступа - пользователя с текущем именем не существует
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> student = repository.findByUsername(username);

        return student.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Incorrect name: " + username));
    }
}