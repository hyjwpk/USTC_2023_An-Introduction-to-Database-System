package com.example.backend.mapper;

import com.example.backend.entity.SavingAccount;
import com.example.backend.dto.SavingInteract;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

@Mapper
public interface SavingAccountMapper {

    @Update("update saving_account set client_id = #{client_id}, bank_name = #{bank_name}, password = #{password}, remaining = #{remaining}, open_date = (case when #{open_date} = '' then null when #{open_date} = #{open_date} then #{open_date} end), interest_rate = #{interest_rate} where account_id = #{account_id}")
    void edit(SavingAccount savingAccount);

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call get_card (1, #{SavingAccount.client_id}, #{SavingAccount.bank_name}, #{SavingAccount.password}, #{SavingAccount.remaining}, NULL, #{SavingAccount.interest_rate}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    Integer add(@Param("SavingAccount") SavingAccount savingAccount, Map<String, Object> map);

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call delete_card (1, #{SavingAccount.account_id}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    Integer delete(@Param("SavingAccount") SavingAccount savingAccount, Map<String, Object> map);

    @Select("select count(*) from saving_account where (account_id = #{account_id} or #{account_id} is null) and client_id like concat('%', #{client_id}, '%') and bank_name like concat('%', #{bank_name}, '%') and password like concat('%', #{password}, '%') and (remaining = #{remaining} or #{remaining} is null) and (open_date = str_to_date(#{open_date}, '%Y-%m-%d') or #{open_date} = '') and (interest_rate = #{interest_rate} or #{interest_rate} is null)")
    Integer count(SavingAccount savingAccount);

    @Select("select account_id, client_id, bank_name, password, remaining, open_date, interest_rate from saving_account where (account_id = #{savingAccount.account_id} or #{savingAccount.account_id} is null) and client_id like concat('%', #{savingAccount.client_id}, '%') and bank_name like concat('%', #{savingAccount.bank_name}, '%') and password like concat('%', #{savingAccount.password}, '%') and (remaining = #{savingAccount.remaining} or #{savingAccount.remaining} is null) and (open_date = str_to_date(#{savingAccount.open_date}, '%Y-%m-%d') or #{savingAccount.open_date} = '') and (interest_rate = #{savingAccount.interest_rate} or #{savingAccount.interest_rate} is null) limit #{start}, #{size}")
    List<SavingAccount> page(Integer start, Integer size, @Param("savingAccount") SavingAccount savingAccount);

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call saving_interact (1, #{SavingInteract.account_id}, #{SavingInteract.money}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    void saving(@Param("SavingInteract") SavingInteract savingInteract, Map<String, Object> map);

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call saving_interact (2, #{SavingInteract.account_id}, #{SavingInteract.money}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    void withdraw(@Param("SavingInteract") SavingInteract savingInteract, Map<String, Object> map);
}