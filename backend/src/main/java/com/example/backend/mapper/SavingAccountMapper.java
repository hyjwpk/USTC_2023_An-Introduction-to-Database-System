package com.example.backend.mapper;

import com.example.backend.entity.SavingAccount;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SavingAccountMapper {

    @Update("update saving_account set client_id = #{client_id}, bank_name = #{bank_name}, password = #{password}, remaining = #{remaining}, open_date = (case when #{open_date} = '' then null when #{open_date} = #{open_date} then #{open_date} end), interest_rate = #{interest_rate} where account_id = #{account_id}")
    void edit(SavingAccount savingAccount);

    @Insert("insert into saving_account(account_id, client_id, bank_name, password, remaining, open_date, interest_rate) values(#{account_id}, #{client_id}, #{bank_name}, #{password}, #{remaining}, (case when #{open_date} = '' then null when #{open_date} = #{open_date} then #{open_date} end), #{interest_rate})")
    void add(SavingAccount savingAccount);

    @Delete("delete from saving_account where account_id = #{account_id}")
    void delete(SavingAccount savingAccount);

    @Select("select count(*) from saving_account where (account_id = #{account_id} or #{account_id} is null) and client_id like concat('%', #{client_id}, '%') and bank_name like concat('%', #{bank_name}, '%') and password like concat('%', #{password}, '%') and (remaining = #{remaining} or #{remaining} is null) and (open_date = str_to_date(#{open_date}, '%Y-%m-%d') or #{open_date} = '') and (interest_rate = #{interest_rate} or #{interest_rate} is null)")
    Integer count(SavingAccount savingAccount);

    @Select("select account_id, client_id, bank_name, password, remaining, open_date, interest_rate from saving_account where (account_id = #{savingAccount.account_id} or #{savingAccount.account_id} is null) and client_id like concat('%', #{savingAccount.client_id}, '%') and bank_name like concat('%', #{savingAccount.bank_name}, '%') and password like concat('%', #{savingAccount.password}, '%') and (remaining = #{savingAccount.remaining} or #{savingAccount.remaining} is null) and (open_date = str_to_date(#{savingAccount.open_date}, '%Y-%m-%d') or #{savingAccount.open_date} = '') and (interest_rate = #{savingAccount.interest_rate} or #{savingAccount.interest_rate} is null)")
    List<SavingAccount> page(Integer start, Integer size, @Param("savingAccount") SavingAccount savingAccount);
}