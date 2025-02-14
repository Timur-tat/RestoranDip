package com.diplom.restoran.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orderss")
@Tag(name = "Orders", description = "Управление заказами в ресторане")
@Schema(description = "Сущность заказа в ресторане")
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор заказа", example = "1")
    private Long id;

    @Schema(description = "Статус заказа", example = "Готово")
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)

    @Schema(description = "Клиент, сделавший заказ")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @Schema(description = "Официант, принявший заказ")
    private Waiter waiter;

    @OneToMany(cascade = CascadeType.ALL)
    @Schema(description = "Список блюд в заказе")
    private List<Dish> dishes= new ArrayList<>();

    @Schema(description = "Общая сумма заказа", example = "1250.50")
    private Double totalAmount;

    @Schema(description = "Оплачен ли заказ", example = "false")
    private Boolean paid;
}