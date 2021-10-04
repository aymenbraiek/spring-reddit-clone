package biat.learning.springredditclone.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("spring.datasource")
public class DBConfiguration {
private String driverClassName;
private String url;
private String username;
private String password;


@Profile("prod")
@Bean
public String devDatabaseConnection(){
    System.out.println("mysql database");
    System.out.println(driverClassName);
    System.out.println(url);
    return "mysql-database";
}

    @Profile("local")
    @Bean
    public String prodDatabaseConnection(){
        System.out.println("postgres database");
        System.out.println(driverClassName);
        System.out.println(url);
        return "postgres-database";
    }
}
