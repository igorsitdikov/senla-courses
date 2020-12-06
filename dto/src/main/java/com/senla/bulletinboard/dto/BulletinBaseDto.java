package com.senla.bulletinboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BulletinBaseDto extends AbstractDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Long id;
    protected String title;
    protected BigDecimal price;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    protected LocalDateTime createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected UserDto seller;
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected Long sellerId;
}
