package com.diplom.restoran.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

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
    @JsonIgnoreProperties("waiter")
    @OneToMany(mappedBy = "waiter")
    @ToString.Exclude
    @Schema(description = "Список заказов, оформленных официантом")

    private List<CustomerOrder> orders;
    public Waiter addToOrders(CustomerOrder order) {
        this.orders.add(order);
        return this;

    }
    public Waiter removeFromOrders(CustomerOrder order) {
        this.orders.remove(order);
        return this;
    }
}