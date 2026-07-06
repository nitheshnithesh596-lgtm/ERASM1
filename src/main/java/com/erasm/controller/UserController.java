package com.erasm.controller;

import java.util.List;
import com.erasm.dto.ChangePasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.erasm.dto.UserDTO;
import com.erasm.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@Valid@RequestBody UserDTO userDTO) {

        UserDTO savedUser = userService.saveUser(userDTO);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        List<UserDTO> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {

        UserDTO user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,@Valid
                                              @RequestBody UserDTO userDTO) {

        UserDTO updatedUser = userService.updateUser(id, userDTO);

        return ResponseEntity.ok(updatedUser);
    }
    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordDTO dto) {

        userService.changePassword(id, dto);

        return ResponseEntity.ok("Password changed successfully.");
    }
   
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully.");
    }
}