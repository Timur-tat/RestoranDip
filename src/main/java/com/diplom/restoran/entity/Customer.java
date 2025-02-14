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
@Schema(description = "Сущность клиента")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Schema(description = "Уникальный идентификатор клиента", example = "7")
    private Long id;

    @Schema(description = "Имя клиента", example = "Александр Смирнов")
    private String name;

    @OneToMany
    @Schema(description = "Список заказов клиента")
    private List<CustomerOrder> orders;
}