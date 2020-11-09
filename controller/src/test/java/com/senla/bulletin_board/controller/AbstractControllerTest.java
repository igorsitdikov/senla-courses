package com.senla.bulletin_board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.bulletin_board.configuration.ApiBasePathConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
    classes = {UserController.class, TariffController.class, BulletinController.class, CommentController.class,
               ApiBasePathConfiguration.class})
@AutoConfigureWebMvc
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
}
