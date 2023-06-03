package com.example.backend.mapper;

import com.example.backend.entity.CreditAccount;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CreditAccountMapper {

    @Select("select account_id, client_id, bank_name, password, remaining, open_date, overdraft from credit_account")
    List<CreditAccount> getList();

    @Update("update credit_account set client_id = #{client_id}, bank_name = #{bank_name}, password = #{password}, remaining = #{remaining}, open_date = (case when #{open_date} = '' then null when #{open_date} = #{open_date} then #{open_date} end), overdraft = #{overdraft} where account_id = #{account_id}")
    void edit(CreditAccount creditAccount);

    @Insert("insert into credit_account(account_id, client_id, bank_name, password, remaining, open_date, overdraft) values(#{account_id}, #{client_id}, #{bank_name}, #{password}, #{remaining}, (case when #{open_date} = '' then null when #{open_date} = #{open_date} then #{open_date} end), #{overdraft})")
    void add(CreditAccount creditAccount);

    @Delete("delete from credit_account where account_id = #{account_id}")
    void delete(CreditAccount creditAccount);

    @Select("select count(*) from credit_account where (account_id = #{account_id} or #{account_id} is null) and client_id like concat('%', #{client_id}, '%') and bank_name like concat('%', #{bank_name}, '%') and password like concat('%', #{password}, '%') and (remaining = #{remaining} or #{remaining} is null) and (open_date = str_to_date(#{open_date}, '%Y-%m-%d') or #{open_date} = '') and (overdraft = #{overdraft} or #{overdraft} is null)")
    Integer count(CreditAccount creditAccount);

    @Select("select account_id, client_id, bank_name, password, remaining, open_date, overdraft from credit_account where (account_id = #{creditAccount.account_id} or #{creditAccount.account_id} is null) and client_id like concat('%', #{creditAccount.client_id}, '%') and bank_name like concat('%', #{creditAccount.bank_name}, '%') and password like concat('%', #{creditAccount.password}, '%') and (remaining = #{creditAccount.remaining} or #{creditAccount.remaining} is null) and (open_date = str_to_date(#{creditAccount.open_date}, '%Y-%m-%d') or #{creditAccount.open_date} = '') and (overdraft = #{creditAccount.overdraft} or #{creditAccount.overdraft} is null)")
    List<CreditAccount> page(Integer start, Integer size, @Param("creditAccount") CreditAccount creditAccount);
}