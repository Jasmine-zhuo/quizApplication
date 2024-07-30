package com.bfs.logindemo.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcUtils {
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/quiz_app");
        dataSource.setUsername("root");
        dataSource.setPassword("Goodluckandhappy999+");
        return dataSource;
    }

    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }
}
