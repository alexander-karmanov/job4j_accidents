package ru.job4j.accidents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/* import org.springframework.boot.autoconfigure.jdbc.JdbcClientAutoConfiguration; */
/* (exclude = {DataSourceAutoConfiguration.class, JdbcClientAutoConfiguration.class}) */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("secret");
        System.out.println(pwd);
    }
}