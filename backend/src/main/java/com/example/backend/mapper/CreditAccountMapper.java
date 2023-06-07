package com.example.backend.mapper;

import com.example.backend.entity.CreditAccount;
import com.example.backend.dto.SavingInteract;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

@Mapper
public interface CreditAccountMapper {

    @Update("update credit_account set client_id = #{client_id}, bank_name = #{bank_name}, password = #{password}, remaining = #{remaining}, open_date = (case when #{open_date} = '' then null when #{open_date} = #{open_date} then #{open_date} end), overdraft = #{overdraft} where account_id = #{account_id}")
    void edit(CreditAccount creditAccount);

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call get_card (2, #{CreditAccount.client_id}, #{CreditAccount.bank_name}, #{CreditAccount.password}, NULL, #{CreditAccount.overdraft}, NULL, #{map.status, mode=OUT, jdbcType=INTEGER});")
    Integer add(@Param("CreditAccount") CreditAccount creditAccount, Map<String, Object> map);

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call delete_card (2, #{CreditAccount.account_id}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    Integer delete(@Param("CreditAccount") CreditAccount creditAccount, Map<String, Object> map);

    @Select("select count(*) from credit_account where (account_id = #{account_id} or #{account_id} is null) and client_id like concat('%', #{client_id}, '%') and bank_name like concat('%', #{bank_name}, '%') and password like concat('%', #{password}, '%') and (remaining = #{remaining} or #{remaining} is null) and (open_date = str_to_date(#{open_date}, '%Y-%m-%d') or #{open_date} = '') and (overdraft = #{overdraft} or #{overdraft} is null)")
    Integer count(CreditAccount creditAccount);

    @Select("select account_id, client_id, bank_name, password, remaining, open_date, overdraft from credit_account where (account_id = #{creditAccount.account_id} or #{creditAccount.account_id} is null) and client_id like concat('%', #{creditAccount.client_id}, '%') and bank_name like concat('%', #{creditAccount.bank_name}, '%') and password like concat('%', #{creditAccount.password}, '%') and (remaining = #{creditAccount.remaining} or #{creditAccount.remaining} is null) and (open_date = str_to_date(#{creditAccount.open_date}, '%Y-%m-%d') or #{creditAccount.open_date} = '') and (overdraft = #{creditAccount.overdraft} or #{creditAccount.overdraft} is null) limit #{start}, #{size}")
    List<CreditAccount> page(Integer start, Integer size, @Param("creditAccount") CreditAccount creditAccount);

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call credit_interact (1, #{SavingInteract.account_id}, #{SavingInteract.money}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    void return_c(@Param("SavingInteract") SavingInteract savingInteract, Map<String, Object> map);

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call credit_interact (2, #{SavingInteract.account_id}, #{SavingInteract.money}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    void lend(@Param("SavingInteract") SavingInteract savingInteract, Map<String, Object> map);
}