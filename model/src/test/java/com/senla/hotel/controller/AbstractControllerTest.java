package com.senla.hotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.hotel.HotelTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = HotelTest.class)
@WebAppConfiguration
public class AbstractControllerTest {

    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper mapper;
}
