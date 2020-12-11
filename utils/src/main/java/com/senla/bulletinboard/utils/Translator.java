package com.senla.bulletinboard.utils;

import lombok.Data;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Data
public class Translator {

    private final ResourceBundleMessageSource messageSource;

    public String toLocale(String msg, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return String.format(this.messageSource.getMessage(msg, null, locale), args);
    }
}
