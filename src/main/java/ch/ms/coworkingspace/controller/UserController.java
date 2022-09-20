package ch.ms.coworkingspace.controller;

import ch.ms.coworkingspace.model.User;
import ch.ms.coworkingspace.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User management endpoints")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get all users",
            description = "Loads all users from the database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @GetMapping
    public ResponseEntity<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Get one specific user",
            description = "Loads one specific user by ID from the database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id){
        return userService.getUserById(id);
    }

    @Operation(
            summary = "Create a new user",
            description = "Creates a new user in database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @Operation(
            summary = "Update an existing user",
            description = "Update information from a specific user by ID.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable UUID id, @RequestBody User user){
        return userService.updateUserById(id, user);
    }

    @Operation(
            summary = "Delete an existing user",
            description = "Delete a user by ID.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable UUID id){
        return userService.deleteUserById(id);
    }





}
