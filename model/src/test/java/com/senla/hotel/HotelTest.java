package com.senla.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.senla.hotel"})
public class HotelTest {

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }
}
