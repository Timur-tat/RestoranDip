package com.diplom.restoran.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO клиента")
public class CustomerDTO {
    private Long id;
    private String name;
    private List<Long> customerOrderIds;
}