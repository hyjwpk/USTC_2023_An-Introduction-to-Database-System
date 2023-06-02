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
    final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Map<String, String> login(User user) {
        Map<String, String> map = new HashMap<>();
        List<User> userList = userMapper.getList();
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

    public Map<String, List<User>> getList() {
        Map<String, List<User>> map = new HashMap<>();
        map.put("data", userMapper.getList());
        return map;
    }

    public Map<String, String> edit(User user) {
        Map<String, String> map = new HashMap<>();
        userMapper.edit(user);
        map.put("code", "0");
        map.put("message", "修改成功");
        return map;
    }

    public Map<String, String> add(User user) {
        Map<String, String> map = new HashMap<>();
        userMapper.add(user);
        map.put("code", "0");
        map.put("message", "添加成功");
        return map;
    }

    public Map<String, String> delete(User user) {
        Map<String, String> map = new HashMap<>();
        userMapper.delete(user);
        map.put("code", "0");
        map.put("message", "删除成功");
        return map;
    }

    public Map<String, Object> page(Integer page, Integer size, String name) {
        Integer start = (page - 1) * size;
        Integer count = userMapper.count(name);
        List<User> userList = userMapper.page(start, size, name);
        Map<String, Object> map = new HashMap<>();
        map.put("data", userList);
        map.put("count", count);
        return map;
    }
}
