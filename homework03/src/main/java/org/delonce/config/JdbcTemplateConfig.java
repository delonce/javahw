package org.delonce.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class JdbcTemplateConfig {
    public static JdbcTemplate jdbcTemplate() {
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=delonce",
                "postgres",
                "Qwerty1234");

        return new JdbcTemplate(dataSource);
    }
}
