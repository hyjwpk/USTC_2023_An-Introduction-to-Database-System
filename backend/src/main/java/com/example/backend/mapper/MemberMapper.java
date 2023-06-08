package com.example.backend.mapper;

import com.example.backend.entity.Member;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {
    @Options(statementType = StatementType.CALLABLE)
    @Select("Call edit_member ( #{member.id},#{member.depart_no},#{member.dep_depart_no},#{member.bank_name},#{member.name},#{member.phone},#{member.address},#{member.salary},#{member.level}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    Integer edit(@Param("member") Member member, Map<String, Object> map);


    @Options(statementType = StatementType.CALLABLE)
    @Select("Call employ ( #{member.dep_depart_no},#{member.bank_name},#{member.name},#{member.sex},#{member.person_id},#{member.phone},#{member.address}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    Integer add(@Param("member") Member member, Map<String, Object> map);

    @Delete("delete from member where id = #{id}")
    void delete(Member member);

    @Select("select count(*) from member where (id = #{id} or #{id} is null) and (depart_no = #{depart_no} or #{depart_no} is null) and (dep_depart_no = #{dep_depart_no} or #{dep_depart_no} is null) and bank_name like concat('%', #{bank_name}, '%')  and name like concat('%', #{name}, '%') and sex like concat('%', #{sex}, '%') and person_id like concat('%', #{person_id}, '%') and phone like concat('%', #{phone}, '%') and address like concat('%', #{address}, '%') and (salary = #{salary} or #{salary} is null) and (begin_date = str_to_date(#{begin_date}, '%Y-%m-%d') or #{begin_date} = '') and (level = #{level} or #{level} is null)")
    Integer count(Member member);

    @Select("select id, depart_no, dep_depart_no, bank_name, name, sex, person_id, phone, address, salary, begin_date, level from member where (id = #{member.id} or #{member.id} is null) and (depart_no = #{member.depart_no} or #{member.depart_no} is null) and (dep_depart_no = #{member.dep_depart_no} or #{member.dep_depart_no} is null) and bank_name like concat('%', #{member.bank_name}, '%')  and name like concat('%', #{member.name}, '%') and sex like concat('%', #{member.sex}, '%') and person_id like concat('%', #{member.person_id}, '%') and phone like concat('%', #{member.phone}, '%') and address like concat('%', #{member.address}, '%') and (salary = #{member.salary} or #{member.salary} is null) and (begin_date = str_to_date(#{member.begin_date}, '%Y-%m-%d') or #{member.begin_date} = '') and (level = #{member.level} or #{member.level} is null) limit #{start}, #{size}")
    List<Member> page(Integer start, Integer size, @Param("member") Member member);
}

