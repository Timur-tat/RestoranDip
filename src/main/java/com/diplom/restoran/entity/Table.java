package com.diplom.restoran.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;



@Builder
@Entity(name="tabless")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор ", example = "1")
    private Long id;

    @NotNull(message = "Номер столика обязателен")
    @Min(value = 1, message = "Номер столика должен быть больше 0")
    @Schema(description = "Уникальный номер стола", example = "1")
    private Integer tableNumber;

    @NotNull(message = "Количество мест обязательно")
    @Min(value = 1, message = "Должно быть минимум 1 место")
    @Schema(description = "Количество мест", example = "10")
    private Integer seats;
}