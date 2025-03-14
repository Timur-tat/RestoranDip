package com.diplom.restoran.security.controller;


import com.diplom.restoran.entity.Customer;
import com.diplom.restoran.repository.CustomerRepository;
import com.diplom.restoran.security.jwt.JwtUtils;
import com.diplom.restoran.security.models.ERole;
import com.diplom.restoran.security.models.Role;
import com.diplom.restoran.security.models.User;
import com.diplom.restoran.security.repository.RoleRepository;
import com.diplom.restoran.security.repository.UserRepository;
import com.diplom.restoran.security.request.LoginRequest;
import com.diplom.restoran.security.request.SignupRequest;
import com.diplom.restoran.security.response.JwtResponse;
import com.diplom.restoran.security.response.MessageResponse;
import com.diplom.restoran.security.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Аутентификация", description = "API для входа и регистрации пользователей")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;
@Autowired
CustomerRepository customerRepository;
  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  @Operation(summary = "Вход в систему", description = "Аутентификация пользователя и получение JWT токена")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity
        .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
  }

  @PostMapping("/signup")
  @Operation(summary = "Регистрация пользователя", description = "Создание нового аккаунта")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          System.out.println("admin");
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
          case "chef":
            Role chefRole = roleRepository.findByName(ERole.ROLE_CHEF)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(chefRole);

            break;
          case "waiter":
            Role waiterRole = roleRepository.findByName(ERole.ROLE_WAITER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(waiterRole);

            break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);
    Customer customer = new Customer();
    customer.setUser(user);
customerRepository.save(customer);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
