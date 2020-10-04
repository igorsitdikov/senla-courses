package com.senla.hotel.config.application;

import com.senla.hotel.config.application.SortFieldEnumConverter.SortFieldEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class RequestConverterConfig extends WebMvcConfigurationSupport {

    @Override
    public FormattingConversionService mvcConversionService() {
        FormattingConversionService f = super.mvcConversionService();
        f.addConverter(new SortFieldEnumConverter());
        return f;
    }
}
