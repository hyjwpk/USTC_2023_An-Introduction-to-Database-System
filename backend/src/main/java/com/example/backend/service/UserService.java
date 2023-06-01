package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    final
    UserMapper bookMapper;

    @Autowired
    public UserService(UserMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public Map<String, String> login(User user) {
        Map<String, String> map = new HashMap<>();
        List<User> userList = bookMapper.getUserList();
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

    public Map<String, List<User>> getUserList() {
        Map<String, List<User>> map = new HashMap<>();
        map.put("data", bookMapper.getUserList());
        return map;
    }

    public Map<String, String> editUser(User user) {
        Map<String, String> map = new HashMap<>();
        bookMapper.editUser(user);
        map.put("code", "0");
        map.put("message", "修改成功");
        return map;
    }

    public Map<String, String> addUser(User user) {
        Map<String, String> map = new HashMap<>();
        bookMapper.addUser(user);
        map.put("code", "0");
        map.put("message", "添加成功");
        return map;
    }

    public Map<String, String> deleteUser(User user) {
        Map<String, String> map = new HashMap<>();
        bookMapper.deleteUser(user);
        map.put("code", "0");
        map.put("message", "删除成功");
        return map;
    }

    public Map<String, Object> page(Integer page, Integer size, String name) {
        Integer start = (page - 1) * size;
        Integer count = bookMapper.count(name);
        List<User> userList = bookMapper.page(start, size, name);
        Map<String, Object> map = new HashMap<>();
        map.put("data", userList);
        map.put("count", count);
        return map;
    }
}
