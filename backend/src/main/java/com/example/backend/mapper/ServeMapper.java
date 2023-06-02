package com.example.backend.mapper;

import com.example.backend.entity.Serve;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ServeMapper {

    @Select("select id, client_id from serve")
    List<Serve> getList();

    @Insert("insert into serve (id, client_id) values (#{id}, #{client_id})")
    void add(Serve serve);

    @Delete("delete from serve where id = #{id} and client_id = #{client_id}")
    void delete(Serve serve);

    @Select("select count(*) from serve where (id = #{id} or #{id} is null) and client_id like concat('%', #{client_id}, '%')")
    Integer count(Serve serve);

    @Select("select id, client_id from serve where (id = #{serve.id} or #{serve.id} is null) and client_id like concat('%', #{serve.client_id}, '%') limit #{start}, #{size}")
    List<Serve> page(Integer start, Integer size, @Param("serve") Serve serve);
}