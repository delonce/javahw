package app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.driver}")
    private String dataDriver;

    @Value("${spring.datasource.url}")
    private String dataUrl;

    @Value("${spring.datasource.username}")
    private String dataUser;

    @Value("${spring.datasource.password}")
    private String dataPassword;

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