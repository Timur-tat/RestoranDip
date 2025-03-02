package com.diplom.restoran.dto;

import com.diplom.restoran.entity.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO блюда")
public class DishDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean available;
    private List<Long> customerOrders = new ArrayList<>();
}
