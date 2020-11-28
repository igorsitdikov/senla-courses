package com.senla.bulletinboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SellerVoteDto extends AbstractDto {

    private Long id;
    private Long voterId;
    private Long bulletinId;
    private Integer vote;
}
