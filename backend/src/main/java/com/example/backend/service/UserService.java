package com.example.backend.service;

import com.example.backend.Book.User;
import com.example.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper bookMapper;

    public List<User> getUserList() {
        return bookMapper.getUserList();
    }

    public void editUser(User user) {
        bookMapper.editUser(user);
    }

    public void addUser(User user) {
        bookMapper.addUser(user);
    }

    public void deleteUser(User user) {
        bookMapper.deleteUser(user);
    }
}
