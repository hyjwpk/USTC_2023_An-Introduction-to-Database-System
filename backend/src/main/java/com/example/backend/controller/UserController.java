package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    final
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        return userService.login(user);
    }

    @GetMapping("/getUserList")
    public Map<String, List<User>> getUserList() {
        return userService.getUserList();
    }

    @PostMapping("/editUser")
    public Map<String, String> editUser(@RequestBody User user) {
        return userService.editUser(user);
    }

    @PostMapping("/addUser")
    public Map<String, String> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/deleteUser")
    public Map<String, String> deleteUser(@RequestBody User user) {
        return userService.deleteUser(user);
    }

    @GetMapping("/page")
    public Map<String, Object> page(@RequestParam Integer page, @RequestParam Integer size, @RequestParam String name) {
        return userService.page(page, size, name);
    }
}

