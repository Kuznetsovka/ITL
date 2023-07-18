package ru.itl.train.cong;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

/**
 * @author Kuznetsovka created 10.08.2022
 */
@Configuration
public class ConfigThymleaf implements WebMvcConfigurer {

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

}
