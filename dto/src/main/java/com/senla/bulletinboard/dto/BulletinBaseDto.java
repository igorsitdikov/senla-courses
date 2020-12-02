package com.senla.bulletinboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BulletinBaseDto extends AbstractDto {

    protected Long id;
    protected String title;
    protected BigDecimal price;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    protected LocalDateTime createdAt;
    protected UserDto author;
}
