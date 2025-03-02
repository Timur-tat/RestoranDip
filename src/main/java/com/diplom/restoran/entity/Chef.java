package com.diplom.restoran.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

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
    @JsonIgnoreProperties("chef")
    @OneToMany(mappedBy = "chef")
    @ToString.Exclude
    @Schema(description = "Заказы, переданные повару на приготовление")
    private List<CustomerOrder> orders;
    public Chef addToOrders(CustomerOrder order) {
        this.orders.add(order);
        return this;
    }
    public Chef removeFromOrders(CustomerOrder order) {
        this.orders.remove(order);
        return this;
    }
}