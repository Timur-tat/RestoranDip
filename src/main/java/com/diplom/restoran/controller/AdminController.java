package com.diplom.restoran.controller;

import com.diplom.restoran.dto.AdminDTO;
import com.diplom.restoran.dto.CustomerOrderDTO;
import com.diplom.restoran.dto.WaiterDTO;
import com.diplom.restoran.entity.Admin;
import com.diplom.restoran.exeption.NotFoundException;



import com.diplom.restoran.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Id;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
    @RequestMapping("/api/admins")
    @Tag(name = "Admin", description = "Управление администраторами")
@PreAuthorize("hasRole('ADMIN')")
    class AdminController {
        private final AdminService adminService;

        public AdminController(AdminService adminService) {
            this.adminService = adminService;
        }

        @GetMapping

        @Operation(summary = "Список всех Admin.", description = "getAllAdmins")
        public List<AdminDTO> getAllAdmins() {
            return adminService.getAllAdmins();
        }
    @PostMapping
    @Operation(summary = "Создание нового Admin", description = "saveAdmin")
    public void saveAdmin(@RequestBody AdminDTO adminDTO) {
        adminService.saveAdmin(adminDTO);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Поиск Admin по id", description = "findById")
    public AdminDTO findById(@PathVariable long id) throws NotFoundException {
        return adminService.findById(id);
    }
    @GetMapping("/waiters/{adminId}")
    @Operation(summary = "Добавляет существующий Waiter в существующий Admin.", description = "saveWaitersToAdmin")
    public Admin saveWaitersToAdmin(@RequestParam List<Long> waitersId, @PathVariable Long adminId ) {
       return adminService.saveWaiterToAdmin(adminId, waitersId);
    }//напомнить сделать конверторы dto в админы и наоборот
    @GetMapping("/chefs/{adminId}")
    @Operation(summary = "Добавляет существующий Chef в существующий Admin.", description = "saveChefToAdmin ")
    public Admin saveChefToAdmin(@RequestParam List<Long> chefsId, @RequestParam Long adminId ) {

            return adminService.saveChefToAdmin(adminId, chefsId);

    }


    }


