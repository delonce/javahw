package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Класс, запускающий приложение и поднятие Spring - контекста
 */
@SpringBootApplication
public class RolesApplication {

    /**
     * Процедура запуска Spring - приложения
     * @param args модификаторы способа запуска приложения
     */
    public static void main(String[] args) {
        SpringApplication.run(RolesApplication.class, args);
    }
}