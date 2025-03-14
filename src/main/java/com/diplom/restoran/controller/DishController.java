package com.diplom.restoran.controller;

import com.diplom.restoran.dto.DishDTO;
import com.diplom.restoran.entity.Dish;
import com.diplom.restoran.exeption.NotFoundException;
import com.diplom.restoran.repository.DishRepository;
import com.diplom.restoran.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
@Tag(name = "Dishes", description = "Управления блюдами")
@PreAuthorize(" hasRole('CHEF') or hasRole('ADMIN')")
 class DishController {
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
        return dishService.getAllDishes();
}
public DishDTO findDishById(@PathVariable long id) throws NotFoundException {
        return dishService.getDishById(id);
}
    @GetMapping("/underPrice")
    @Operation(summary = "Список всех Dishes до указанной суммы.", description = "getDishesUnderPrice")
    public List<DishDTO> getDishesUnderPrice(@RequestParam Double maxPrice) {
        return dishService.getDishesUnderPrice(maxPrice);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление Dish по id.", description = "deleteDish")
    public void deleteDish(@PathVariable Long id) {
        try {
            dishService.deleteDish(id);
        } catch (NotFoundException e) {
            throw new NotFoundException("dish not found"+ id);
        }
    }
}
