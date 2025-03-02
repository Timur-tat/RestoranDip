package com.diplom.restoran.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema(description = "Сущность администратора ресторана")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор администратора", example = "1")
    private Long id;

    @Schema(description = "Имя администратора", example = "Сергей Иванов")
    private String name;

    @OneToMany
    @Schema(description = "Список официантов в подчинении")
    private List<Waiter> waiters= new ArrayList<>();//add remove

    @OneToMany
    @Schema(description = "Список поваров в подчинении")
    private List<Chef> chefs= new ArrayList<>();//add remove

    @OneToMany
    @Schema(description = "Меню ресторана")
    private List<Dish> menu= new ArrayList<>();//add remove


}