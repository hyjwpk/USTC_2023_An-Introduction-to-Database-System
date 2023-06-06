package com.example.backend.mapper;

import com.example.backend.entity.RenameBank;
import com.example.backend.entity.SavingInteract;
import com.example.backend.entity.SubBank;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubBankMapper {

    @Update("update sub_bank set bank_location = #{bank_location}, asset = #{asset} where bank_name = #{bank_name}")
    void edit(SubBank subBank);

    @Insert("insert into sub_bank(bank_name, bank_location, asset) values(#{bank_name}, #{bank_location}, #{asset})")
    void add(SubBank subBank);

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call bank_rename (#{RenameBank.old_name}, #{RenameBank.new_name}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    Integer rename(@Param("RenameBank") RenameBank renameBank, Map<String,Object> map);

    @Delete("delete from sub_bank where bank_name = #{bank_name}")
    void delete(SubBank subBank);

    @Select("select count(*) from sub_bank where bank_name like concat('%', #{bank_name}, '%') and bank_location like concat('%', #{bank_location}, '%') and (asset = #{asset} or #{asset} is null)")
    Integer count(SubBank subBank);

    @Select("select bank_name, bank_location, asset from sub_bank where bank_name like concat('%', #{subBank.bank_name}, '%') and bank_location like concat('%', #{subBank.bank_location}, '%') and (asset = #{subBank.asset} or #{subBank.asset} is null) limit #{start}, #{size}")
    List<SubBank> page(Integer start, Integer size, @Param("subBank") SubBank subBank);
}