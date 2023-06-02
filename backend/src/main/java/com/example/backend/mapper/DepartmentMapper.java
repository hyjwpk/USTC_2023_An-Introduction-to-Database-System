package com.example.backend.mapper;

import com.example.backend.entity.Client;
import com.example.backend.entity.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    @Select("select depart_no, id, bank_name, depart_name, depart_type from department")
    List<Department> getList();

    @Update("update department set id = #{id}, bank_name = #{bank_name}, depart_name = #{depart_name}, depart_type = #{depart_type} where depart_no = #{depart_no}")
    void edit(Department department);

    @Insert("insert into department(depart_no, id, bank_name, depart_name, depart_type) values(#{depart_no}, #{id}, #{bank_name}, #{depart_name}, #{depart_type})")
    void add(Department department);

    @Delete("delete from department where depart_no = #{depart_no}")
    void delete(Department department);

    @Select("select count(*) from department where (depart_no = #{depart_no} or #{depart_no} is null) and (id = #{id} or #{id} is null) and bank_name like concat('%', #{bank_name}, '%') and depart_name like concat('%', #{depart_name}, '%') and depart_type like concat('%', #{depart_type}, '%')")
    Integer count(Department department);

    @Select("select depart_no, id, bank_name, depart_name, depart_type from department where (depart_no = #{department.depart_no} or #{department.depart_no} is null) and (id = #{department.id} or #{department.id} is null) and bank_name like concat('%', #{department.bank_name}, '%') and depart_name like concat('%', #{department.depart_name}, '%') and depart_type like concat('%', #{department.depart_type}, '%') limit #{start}, #{size}")
    List<Department> page(Integer start, Integer size, @Param("department") Department department);
}
