package com.geekbrains;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Bean
    public Faker getFaker(){
        Faker faker = new Faker();
        return faker;
    }
}
