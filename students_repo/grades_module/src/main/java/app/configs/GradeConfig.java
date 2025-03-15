package app.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class GradeConfig {

    @Value("${spring.datasource.driver}")
    private String dataDriver;

    @Value("${spring.datasource.url}")
    private String dataUrl;

    @Value("${spring.datasource.username}")
    private String dataUser;

    @Value("${spring.datasource.password}")
    private String dataPassword;

    @Value("${spring.datasource.hikari.schema}")
    private String dataSchema;

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataDriver);
        dataSource.setSchema(dataSchema);
        dataSource.setUrl(dataUrl);
        dataSource.setUsername(dataUser);
        dataSource.setPassword(dataPassword);

        return new JdbcTemplate(dataSource);
    }
}