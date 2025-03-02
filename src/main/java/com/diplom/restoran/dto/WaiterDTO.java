package com.diplom.restoran.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO официанта")
public class WaiterDTO {
    @Schema(description = "Уникальный идентификатор официанта", example = "1")
    private Long id;

    @Schema(description = "Имя официанта", example = "Иван Иванов")
    private String name;

    @Schema(description = "Список ID заказов, выполненных официантом", example = "[101, 102, 103]")
    private List<Long> orderIds;
}
