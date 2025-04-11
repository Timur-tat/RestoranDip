package com.diplom.restoran.controller;

import com.diplom.restoran.dto.DishDTO;
import com.diplom.restoran.entity.Dish;
import com.diplom.restoran.exeption.NotFoundException;
import com.diplom.restoran.repository.DishRepository;
import com.diplom.restoran.service.CurrentUserSecurity;
import com.diplom.restoran.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/dishes")
@Tag(name = "Dishes", description = "Управления блюдами")
 class DishController extends CurrentUserSecurity {
    private final DishRepository dishRepository;
    private final DishService dishService;

    public DishController(DishRepository dishRepository, DishService dishService) {this.dishRepository = dishRepository;
        this.dishService = dishService;
    }
@PostMapping
@Operation(summary = "Создать новый Dish.", description = "saveDish")
    public void saveDish(@RequestBody DishDTO dishDTO) {
        dishService.saveDish(dishDTO);
}
@GetMapping
@Operation(summary = "Список всех Dishes.", description = "getAllDishes")
    public List<DishDTO> getAllDishes() {
    log.info("Запрос на получение всех блюд {}",  getCurrentUsername());
        return dishService.getAllDishes();
}
@GetMapping("/{id}")
@Operation(summary = "Поиск блюда по id",description = "findDishById")
public DishDTO findDishById(@PathVariable long id) throws NotFoundException {
    log.info("Запрос на получение блюда по ID: {}, {}", id, getCurrentUsername());
        return dishService.getDishById(id);
}
    @GetMapping("/underPrice")
    @Operation(summary = "Список всех Dishes до указанной суммы.", description = "getDishesUnderPrice")
    public List<DishDTO> getDishesUnderPrice(@RequestParam Double maxPrice) {
        log.info("Запрос на получение блюд до х цены: {}, {}", maxPrice, getCurrentUsername());
        return dishService.getDishesUnderPrice(maxPrice);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление Dish по id.", description = "deleteDish")
    public void deleteDish(@PathVariable Long id) {
        log.info("Запрос на удаление блюда по ID: {}, {}", id, getCurrentUsername());
        try {
            dishService.deleteDish(id);
        } catch (NotFoundException e) {
            log.error("Не найдено блюдо с ID: {}, {}", id, getCurrentUsername());
            throw new NotFoundException("dish not found"+ id);
        }
    }
}
