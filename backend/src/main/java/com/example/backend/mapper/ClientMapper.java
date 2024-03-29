package com.example.backend.mapper;

import com.example.backend.entity.Client;
import com.example.backend.entity.Member;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClientMapper {

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call edit_client ( #{client.client_ID},#{client.real_name},#{client.client_phone},#{client.client_address},#{client.client_email}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    Integer edit(@Param("client") Client client, Map<String, Object> map);

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call register ( #{client.client_ID},#{client.real_name},#{client.client_phone},#{client.client_address},#{client.client_email}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    Integer add(@Param("client") Client client, Map<String, Object> map);

    @Delete("delete from client where client_ID = #{client_ID}")
    void delete(Client client);

    @Select("select count(*) from client where client_ID like concat('%', #{client_ID}, '%') and real_name like concat('%', #{real_name}, '%') and client_phone like concat('%', #{client_phone}, '%') and client_address like concat('%', #{client_address}, '%') and client_email like concat('%', #{client_email}, '%')")
    Integer count(Client client);

    @Select("select client_ID, real_name, client_phone, client_address, client_email from client where client_ID like concat('%', #{client.client_ID}, '%') and real_name like concat('%', #{client.real_name}, '%') and client_phone like concat('%', #{client.client_phone}, '%') and client_address like concat('%', #{client.client_address}, '%') and client_email like concat('%', #{client.client_email}, '%') limit #{start}, #{size}")
    List<Client> page(Integer start, Integer size, @Param("client") Client client);
}