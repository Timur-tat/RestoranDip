package com.diplom.restoran.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema(description = "Сущность повара")
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор повара", example = "2")
    private Long id;

    @Schema(description = "Имя повара", example = "Анна Сидорова")
    private String name;

    @OneToMany
    @Schema(description = "Заказы, переданные повару на приготовление")
    private List<CustomerOrder> orders;
}