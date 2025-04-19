package app.repositories;

import app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Интерфейс доступа к записям таблицы users
 * @author Danma
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Процедура получения данных о пользователе по его имени
     * @param username имя пользвователя
     * @return запись с данными о пользователе или информация об ее отсутствии
     */
    Optional<User> findByUsername(String username);
}