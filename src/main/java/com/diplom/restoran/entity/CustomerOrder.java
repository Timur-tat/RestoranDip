package com.diplom.restoran.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
@Tag(name = "Orders", description = "Управление заказами в ресторане")
@Schema(description = "Сущность заказа в ресторане")
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор заказа", example = "1")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @Schema(description = "повар который готовил заказ")
    private Chef chef;

    @Schema(description = "Статус заказа", example = "Готово")
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("orders")
    @Schema(description = "Клиент, сделавший заказ")
    private Customer customer;

    @JsonIgnoreProperties("orders")
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    @Schema(description = "Официант, принявший заказ")
    private Waiter waiter;

    @ManyToMany
    @JoinTable(
            name = "orders_dishes",
            joinColumns = @JoinColumn(name = "customerOrderId"),
            inverseJoinColumns = @JoinColumn(name = "dishes_id")
    )

    private List<Dish> dishes = new ArrayList<>();

    @Schema(description = "Общая сумма заказа", example = "1250.50")
    private Double totalAmount;
    public void setAllPriceTotalAmount() {
        this.totalAmount = this.dishes.stream() .mapToDouble(Dish::getPrice).sum();
    }

    @Schema(description = "Оплачен ли заказ", example = "false")
    private Boolean paid = false;
}