package com.diplom.restoran.service;

import com.diplom.restoran.dto.AdminDTO;
import com.diplom.restoran.entity.Admin;
import com.diplom.restoran.repository.AdminRepository;
import com.diplom.restoran.repository.ChefRepository;
import com.diplom.restoran.repository.DishRepository;
import com.diplom.restoran.repository.WaiterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
@Mock
private  AdminRepository adminRepository;
    @Mock
    private  WaiterRepository waiterRepository;
    @Mock
    private  ChefRepository chefRepository;
    @Mock
    private  DishRepository dishRepository;
    @InjectMocks
    private AdminService adminService;
    Admin admin1;
    Admin admin2;
    Admin admin3;
    AdminDTO adminDTO1;
    AdminDTO adminDTO2;
    AdminDTO adminDTO3;
    List<Admin> adminList=new ArrayList<>();
    List<AdminDTO> adminDTOList=new ArrayList<>();
    @BeforeEach
    void setUp() {
      admin1=new Admin();
      admin2=new Admin();
      admin3=new Admin();
      adminDTO1=new AdminDTO();
      adminDTO2=new AdminDTO();
      adminDTO3=new AdminDTO();

        adminList.add(admin1);
        adminList.add(admin2);
        adminList.add(admin3);
        adminDTOList.add(adminDTO1);
        adminDTOList.add(adminDTO2);
        adminDTOList.add(adminDTO3);



    }

    @AfterEach
    void tearDown() {
        adminList.removeAll(adminList);
        admin1=null;
        admin2=null;
        admin3=null;
        adminDTOList.removeAll(adminDTOList);
        adminDTO1=null;
        adminDTO2=null;
        adminDTO3=null;
    }

    @Test
    void getAllAdmins() {
        when(adminRepository.findAll()).thenReturn(adminList);
        assertNotEquals(adminList,adminService.getAllAdmins());
        assertEquals(adminList.size(),adminService.getAllAdmins().size());
        assertEquals(adminDTOList,adminService.getAllAdmins().stream()
                .map(admin -> new AdminDTO(admin.getId(), admin.getName(), null, null, null))
                .collect(Collectors.toList()));
    }

    @Test
    void getWaitersByIds() {
    }

    @Test
    void saveAdmin() {
    }

    @Test
    void saveWaiterToAdmin() {
    }

    @Test
    void saveChefToAdmin() {
    }

    @Test
    void findById() {
        when(adminRepository.findById(1l)).thenReturn(Optional.of(admin1));
        adminService.findById(1l);
    }
}