package com.senla.bulletin_board.configuration;

import com.senla.bulletin_board.enumerated.SortBulletin;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SortFieldEnumConverter implements Converter<String, SortBulletin> {

    @Override
    public SortBulletin convert(final String s) {
        try {
            return SortBulletin.valueOf(s.toUpperCase());
        } catch (Exception e) {
            return SortBulletin.DEFAULT;
        }
    }
}
