package com.bfs.logindemo;

import com.bfs.logindemo.config.HibernateProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// Entry point of the application.
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class})
@ServletComponentScan
@EntityScan("com.bfs.logindemo.domain")
//@EnableJpaRepositories("com.bfs.logindemo.dao")
@EnableConfigurationProperties(HibernateProperty.class)
@EnableTransactionManagement
public class quizApplication {

    public static void main(String[] args) {
        SpringApplication.run(quizApplication.class, args);
    }

}
