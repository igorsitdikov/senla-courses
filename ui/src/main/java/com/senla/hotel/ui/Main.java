package com.senla.hotel.ui;

import com.senla.hotel.ui.menu.MenuController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class Main {

    public static void main(final String[] args) throws Exception {
        final ApplicationContext context = new AnnotationConfigApplicationContext("com.senla.hotel");
        final MenuController menuController = context.getBean(MenuController.class);
        menuController.run();
    }
}
