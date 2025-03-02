package com.diplom.restoran.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO повара")
public class ChefDTO {
    private Long id;
    private String name;
    private List<Long> orderIds;
}