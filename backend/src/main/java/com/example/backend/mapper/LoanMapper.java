package com.example.backend.mapper;

import com.example.backend.entity.Loan;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

@Mapper
public interface LoanMapper {

    @Update("update loan set client_id = #{client_id}, bank_name = #{bank_name}, loan_total = #{loan_total}, remain_loan = #{remain_loan}, loan_date = (case when #{loan_date} = '' then null when #{loan_date} = #{loan_date} then #{loan_date} end), loan_rate = #{loan_rate} where loan_id = #{loan_id}")
    void edit(Loan loan);

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call borrow_loan ( #{Loan.client_id},#{Loan.bank_name},#{Loan.loan_total},#{Loan.loan_rate}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    Integer add(@Param("Loan") Loan loan, Map<String, Object> map);

    @Delete("delete from loan where loan_id = #{loan_id}")
    void delete(Loan loan);

    @Select("select count(*) from loan where (loan_id = #{loan_id} or #{loan_id} is null) and client_id like concat('%', #{client_id}, '%') and bank_name like concat('%', #{bank_name}, '%') and (loan_total = #{loan_total} or #{loan_total} is null) and (remain_loan = #{remain_loan} or #{remain_loan} is null) and (loan_date = str_to_date(#{loan_date}, '%Y-%m-%d') or #{loan_date} = '') and (loan_rate = #{loan_rate} or #{loan_rate} is null)")
    Integer count(Loan loan);

    @Select("select loan_id, client_id, bank_name, loan_total, remain_loan, loan_date, loan_rate from loan where (loan_id = #{loan.loan_id} or #{loan.loan_id} is null) and client_id like concat('%', #{loan.client_id}, '%') and bank_name like concat('%', #{loan.bank_name}, '%') and (loan_total = #{loan.loan_total} or #{loan.loan_total} is null) and (remain_loan = #{loan.remain_loan} or #{loan.remain_loan} is null) and (loan_date = str_to_date(#{loan.loan_date}, '%Y-%m-%d') or #{loan.loan_date} = '') and (loan_rate = #{loan.loan_rate} or #{loan.loan_rate} is null) limit #{start}, #{size}")
    List<Loan> page(Integer start, Integer size, @Param("loan") Loan loan);
}