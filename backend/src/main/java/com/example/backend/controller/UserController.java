package com.example.backend.controller;

import com.example.backend.Book.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        Map<String, String> map = new HashMap<>();
        List<User> userList = userService.getUserList();
        for (User u : userList) {
            if (u.getName().equals(user.getName()) && u.getPassword().equals(user.getPassword())) {
                map.put("code", "0");
                map.put("message", "登录成功");
                return map;
            }
        }
        map.put("code", "1");
        map.put("message", "登录失败");
        return map;
    }

    @GetMapping("/getUserList")
    public Map<String, List<User>> getUserList() {
        Map<String, List<User>> map = new HashMap<>();
        map.put("data", userService.getUserList());
        return map;
    }

    @PostMapping("/editUser")
    public Map<String, String> editUser(@RequestBody User user) {
        Map<String, String> map = new HashMap<>();
        userService.editUser(user);
        map.put("code", "0");
        map.put("message", "修改成功");
        return map;
    }

    @PostMapping("/addUser")
    public Map<String, String> addUser(@RequestBody User user) {
        Map<String, String> map = new HashMap<>();
        userService.addUser(user);
        map.put("code", "0");
        map.put("message", "添加成功");
        return map;
    }

    @PostMapping("/deleteUser")
    public Map<String, String> deleteUser(@RequestBody User user) {
        Map<String, String> map = new HashMap<>();
        userService.deleteUser(user);
        map.put("code", "0");
        map.put("message", "删除成功");
        return map;
    }
}

