package bus_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Класс, запускающий приложение и поднятие Spring - контекста
 */
@SpringBootApplication
public class BusApplication {

    /**
     * Процедура запуска Spring - приложения
     * @param args модификаторы способа запуска
     */
    public static void main(String[] args) {
        SpringApplication.run(BusApplication.class, args);
    }
}