package com.example.backend.service;

import com.example.backend.common.MyException;
import com.example.backend.common.Response;
import com.example.backend.common.ResponseEnum;
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

    public Response login(User user) {
        List<User> userList = userMapper.getList();
        for (User u : userList) {
            if (u.getName().equals(user.getName()) && u.getPassword().equals(user.getPassword())) {
                return new Response(200, "登录成功", null);
            }
        }
        throw new MyException(ResponseEnum.FAIL);
    }

    public Response edit(User user) {
        try {
            userMapper.edit(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response add(User user) {
        try {
            userMapper.add(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response delete(User user) {
        try {
            userMapper.delete(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response page(Integer page, Integer size, User user) {
        Integer start = (page - 1) * size;
        Integer count = userMapper.count(user);
        List<User> userList = userMapper.page(start, size, user);
        Map<String, Object> map = new HashMap<>();
        map.put("data", userList);
        map.put("count", count);
        return Response.success(map);
    }
}
