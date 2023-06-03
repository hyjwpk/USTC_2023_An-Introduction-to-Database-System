package com.example.backend.mapper;

import com.example.backend.entity.Client;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClientMapper {

    @Update("update client set real_name = #{real_name}, client_phone = #{client_phone}, client_address = #{client_address}, client_email = #{client_email} where client_ID = #{client_ID}")
    void edit(Client client);

    @Insert("insert into client(client_ID, real_name, client_phone, client_address, client_email) values(#{client_ID}, #{real_name}, #{client_phone}, #{client_address}, #{client_email})")
    void add(Client client);

    @Delete("delete from client where client_ID = #{client_ID}")
    void delete(Client client);

    @Select("select count(*) from client where client_ID like concat('%', #{client_ID}, '%') and real_name like concat('%', #{real_name}, '%') and client_phone like concat('%', #{client_phone}, '%') and client_address like concat('%', #{client_address}, '%') and client_email like concat('%', #{client_email}, '%')")
    Integer count(Client client);

    @Select("select client_ID, real_name, client_phone, client_address, client_email from client where client_ID like concat('%', #{client.client_ID}, '%') and real_name like concat('%', #{client.real_name}, '%') and client_phone like concat('%', #{client.client_phone}, '%') and client_address like concat('%', #{client.client_address}, '%') and client_email like concat('%', #{client.client_email}, '%') limit #{start}, #{size}")
    List<Client> page(Integer start, Integer size, @Param("client") Client client);
}