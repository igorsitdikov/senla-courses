package com.senla.bulletinboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DialogDto extends AbstractDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String title;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    private Long sellerId;
    //    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long customerId;
    //    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long bulletinId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    @JsonIgnore
    private UserDto user;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    private BulletinDto bulletin;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
}
