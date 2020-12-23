package com.senla.bulletinboard.converter;

import com.senla.bulletinboard.enumerated.SortBulletin;
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
