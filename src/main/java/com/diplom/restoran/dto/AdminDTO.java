package com.diplom.restoran.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO Администратора")
public class AdminDTO {
    private Long id;
    private String name;
    private List<Long> waiterIds;
    private List<Long> chefIds;
    private List<Long> menuIds;

}