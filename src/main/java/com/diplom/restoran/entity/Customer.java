package com.diplom.restoran.entity;

import com.diplom.restoran.security.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

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
@ToString.Exclude
    @OneToMany(mappedBy = "customer")
    @Schema(description = "Список заказов клиента")
    private List<CustomerOrder> orders;
    public Customer addToOrders(CustomerOrder order) {
        this.orders.add(order);
        return this;
    }
    public Customer removeFromOrders(CustomerOrder order) {
        this.orders.remove(order);
        return this;
    }
    @OneToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}