package com.diplom.restoran.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO заказа")
public class CustomerOrderDTO {
    private Long id;
    private String status;
    private Long customerId;
    private Long waiterId;
    private List<Long> dishIds;
    private Double totalAmount;
    private Boolean paid =false;
}