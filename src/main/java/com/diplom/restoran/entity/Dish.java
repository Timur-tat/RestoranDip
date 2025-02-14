package com.diplom.restoran.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название блюда обязательно")
    @Size(min = 3, max = 50, message = "Название должно содержать от 3 до 50 символов")
    @Schema(description = "Название блюда", example = "Стейк")
    private String name;

    @NotNull(message = "Цена обязательна")
    @DecimalMin(value = "0.1", message = "Цена блюда должна быть больше 0")
    @Schema(description = "Цена", example = "1000")
    private BigDecimal price;

    @Size(max = 255, message = "Описание не должно превышать 255 символов")
    private String description;

    @NotNull(message = "Поле доступности обязательно")
    private Boolean available;
}