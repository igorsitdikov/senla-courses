package com.senla.hotel.config.application.SortFieldEnumConverter;

import com.senla.hotel.enumerated.SortField;
import org.springframework.core.convert.converter.Converter;

public class SortFieldEnumConverter implements Converter<String, SortField> {

    @Override
    public SortField convert(final String s) {
        try {
            return SortField.valueOf(s.toUpperCase());
        } catch (Exception e) {
            return SortField.DEFAULT;
        }
    }
}
