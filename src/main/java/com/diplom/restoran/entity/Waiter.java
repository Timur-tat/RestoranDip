package com.diplom.restoran.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Schema(description = "Сущность официанта")
public class Waiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор официанта", example = "3")

    private Long id;

    @Schema(description = "Имя официанта", example = "Иван Петров")
    private String name;

    @OneToMany
    @Schema(description = "Список заказов, оформленных официантом")
    private List<CustomerOrder> orders;
}