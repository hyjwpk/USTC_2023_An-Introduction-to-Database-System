package com.example.backend.mapper;

import com.example.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select name, password from user")
    List<User> getUserList();

    @Select("update user set password = #{password} where name = #{name}")
    void editUser(User user);

    @Select("insert into user(name, password) values(#{name}, #{password})")
    void addUser(User user);

    @Select("delete from user where name = #{name}")
    void deleteUser(User user);
}