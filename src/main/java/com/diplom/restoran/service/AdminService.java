package com.diplom.restoran.service;

import com.diplom.restoran.dto.AdminDTO;
import com.diplom.restoran.entity.*;
import com.diplom.restoran.exeption.NotFoundException;
import com.diplom.restoran.repository.*;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
   public class AdminService {
        private final AdminRepository adminRepository;
    private final WaiterRepository waiterRepository;
    private final ChefRepository chefRepository;
    private final DishRepository dishRepository;
    private final TableRepository tableRepository;


    public AdminService(AdminRepository adminRepository, WaiterRepository waiterRepository, ChefRepository chefRepository, DishRepository dishRepository, TableRepository tableRepository) {
            this.adminRepository = adminRepository;
        this.waiterRepository = waiterRepository;
        this.chefRepository = chefRepository;
        this.dishRepository = dishRepository;
        this.tableRepository = tableRepository;
    }

        public List<AdminDTO> getAllAdmins() {
            return adminRepository.findAll().stream()
                    .map(admin -> new AdminDTO(admin.getId(), admin.getName(), null, null, null))
                    .collect(Collectors.toList());
        }
    public Admin saveAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin();

        admin.setName(adminDTO.getName());

        List<Waiter> waiters = adminDTO.getWaiterIds().stream()
                .map(id -> waiterRepository.findById(id).orElseThrow(()->new NotFoundException(""))).collect(Collectors.toList());

        List<Chef> chefs = adminDTO.getChefIds().stream()
                .map(id -> chefRepository.findById(id).orElseThrow(() -> new NotFoundException("Chef not found with id: " + id)))
                .collect(Collectors.toList());

        List<Dish> menu = adminDTO.getMenuIds().stream()
                .map(id -> dishRepository.findById(id).orElseThrow(() -> new NotFoundException("Dish not found with id: " + id)))
                .collect(Collectors.toList());



        admin.setWaiters(waiters);
        admin.setChefs(chefs);
        admin.setMenu(menu);


       return adminRepository.save(admin);
    }
    public Admin saveWaiterToAdmin(Long adminId, List<Long> waiterIds){
        Admin admin = adminRepository.findById(adminId).orElseThrow(()->new NotFoundException("Admin not found with id: " + adminId));
        List<Waiter> waiters =waiterIds.stream()
                .map(id -> waiterRepository.findById(id).orElseThrow(()->new NotFoundException(""))).collect(Collectors.toList());
        admin.setWaiters(waiters);
return adminRepository.save(admin);

    }
    public Admin saveChefToAdmin(Long adminId, List<Long> chefIds){
        Admin admin =adminRepository.findById(adminId).orElseThrow(()->new NotFoundException("Admin not found with id: " + adminId));
        List<Chef> chefs = chefIds.stream()
                .map(id -> chefRepository.findById(id).orElseThrow(() -> new NotFoundException(""))).collect(Collectors.toList());
        admin.setChefs(chefs);
        return adminRepository.save(admin);
    }
    public AdminDTO findById(long id) throws NotFoundException {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isEmpty()) {
            throw new NotFoundException("Admin with id " + id + " not found.");
        }
        Admin admin = optionalAdmin.get();
        return new AdminDTO(admin.getId(), admin.getName(), admin.getWaiters().stream().map(Waiter::getId).collect(Collectors.toList()),
                admin.getChefs().stream().map(Chef::getId).collect(Collectors.toList()),
                admin.getMenu().stream().map(Dish::getId).collect(Collectors.toList()));

    }
//    public AdminDTO saveDishToAdmin(Long adminId, List<Long> dishIds){
//        Admin admin = adminRepository.findById(adminId).orElseThrow(()->new NotFoundException("Admin not found with id: " + adminId));
//        List<Menu> menus = admin.getMenu().stream()
//                .map(id-> dishRepository.findById(id).orElseThrow(()-> new NotFoundException())).collect(Collectors.toList());
//        admin.setMenu(menus);
//        return adminRepository.save(admin)
//    }
}