package ch.ms.coworkingspace.controller;

import ch.ms.coworkingspace.model.User;
import ch.ms.coworkingspace.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/id")
    public ResponseEntity<User> getUserById(Long id){
        return userService.getUserById(id);
    }




}
