package com.senla.bulletinboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SellerVoteDto extends AbstractDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull(message = "voter id is null")
    private Long voterId;
    @NotNull(message = "bulletin id is null")
    private Long bulletinId;
    @NotNull(message = "vote is null")
    @Range(min = 0, max = 5, message = "vote must be in range 0 - 5")
    private Integer vote;
}
