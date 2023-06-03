package com.example.backend.controller;

import com.example.backend.common.Response;
import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import com.example.backend.util.PassToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PassToken
    @PostMapping("/login")
    public Response login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/edit")
    public Response edit(@RequestBody User user) {
        return userService.edit(user);
    }

    @PostMapping("/add")
    public Response add(@RequestBody User user) {
        return userService.add(user);
    }

    @PostMapping("/delete")
    public Response delete(@RequestBody User user) {
        return userService.delete(user);
    }

    @PostMapping("/page")
    public Response page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody User user) {
        return userService.page(page, size, user);
    }
}

