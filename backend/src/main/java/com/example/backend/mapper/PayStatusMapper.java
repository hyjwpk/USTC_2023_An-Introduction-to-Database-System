package com.example.backend.mapper;

import com.example.backend.entity.Loan;
import com.example.backend.entity.PayStatus;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

@Mapper
public interface PayStatusMapper {

    @Update("update pay_status set loan_id = #{loan_id}, pay_money = #{pay_money}, pay_date = (case when #{pay_date} = '' then null when #{pay_date} = #{pay_date} then #{pay_date} end) where pay_id = #{pay_id}")
    void edit(PayStatus payStatus);


    @Options(statementType = StatementType.CALLABLE)
    @Select("Call return_loan (#{PayStatus.loan_id}, #{PayStatus.pay_money}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    Integer add(@Param("PayStatus") PayStatus payStatus, Map<String,Object> map);

    @Delete("delete from pay_status where pay_id = #{pay_id}")
    void delete(PayStatus payStatus);

    @Select("select count(*) from pay_status where (pay_id = #{pay_id} or #{pay_id} is null) and (loan_id = #{loan_id} or #{loan_id} is null) and (pay_money = #{pay_money} or #{pay_money} is null) and (pay_date = str_to_date(#{pay_date}, '%Y-%m-%d') or #{pay_date} = '')")
    Integer count(PayStatus payStatus);

    @Select("select pay_id, loan_id, pay_money, pay_date from pay_status where (pay_id = #{payStatus.pay_id} or #{payStatus.pay_id} is null) and (loan_id = #{payStatus.loan_id} or #{payStatus.loan_id} is null) and (pay_money = #{payStatus.pay_money} or #{payStatus.pay_money} is null) and (pay_date = str_to_date(#{payStatus.pay_date}, '%Y-%m-%d') or #{payStatus.pay_date} = '') limit #{start}, #{size}")
    List<PayStatus> page(Integer start, Integer size, @Param("payStatus") PayStatus payStatus);
}