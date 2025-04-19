package app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Класс конфигурации инструментов доступа к базе данных
 * @author Danma
 * */
@Configuration
public class DataSourceConfig {

    /** Поле драйвера средства доступа к базе данных */
    @Value("${spring.datasource.driver}")
    private String dataDriver;

    /** Поле url базы данных */
    @Value("${spring.datasource.url}")
    private String dataUrl;

    /** Поле имени пользователя базы данных */
    @Value("${spring.datasource.username}")
    private String dataUser;

    /** Поле пароля пользователя базы данных */
    @Value("${spring.datasource.password}")
    private String dataPassword;

    /**
     * Процедура сборки менеджера доступа к базе данных
     * @return dataSource - средство доступа к базе даныых
     * */
    @Bean
    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataDriver);
        dataSource.setUrl(dataUrl);
        dataSource.setUsername(dataUser);
        dataSource.setPassword(dataPassword);

        return dataSource;
    }
}