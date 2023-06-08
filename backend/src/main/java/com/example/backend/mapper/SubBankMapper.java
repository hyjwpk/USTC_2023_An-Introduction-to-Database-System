package com.example.backend.mapper;

import com.example.backend.dto.RenameBank;
import com.example.backend.entity.SubBank;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubBankMapper {

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call edit_sub_bank (#{SubBank.bank_name}, #{SubBank.bank_location}, #{SubBank.asset}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    Integer edit(@Param("SubBank") SubBank subBank, Map<String, Object> map);

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call build_bank (#{SubBank.bank_name}, #{SubBank.bank_location}, #{SubBank.asset}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    Integer add(@Param("SubBank") SubBank subBank, Map<String, Object> map);

    @Delete("delete from sub_bank where bank_name = #{bank_name}")
    void delete(SubBank subBank);

    @Select("select count(*) from sub_bank where bank_name like concat('%', #{bank_name}, '%') and bank_location like concat('%', #{bank_location}, '%') and (asset = #{asset} or #{asset} is null)")
    Integer count(SubBank subBank);

    @Select("select bank_name, bank_location, asset from sub_bank where bank_name like concat('%', #{subBank.bank_name}, '%') and bank_location like concat('%', #{subBank.bank_location}, '%') and (asset = #{subBank.asset} or #{subBank.asset} is null) limit #{start}, #{size}")
    List<SubBank> page(Integer start, Integer size, @Param("subBank") SubBank subBank);

    @Options(statementType = StatementType.CALLABLE)
    @Select("Call bank_rename (#{RenameBank.name}, #{RenameBank.name_new}, #{map.status, mode=OUT, jdbcType=INTEGER});")
    void rename(@Param("RenameBank") RenameBank renameBank, Map<String, Object> map);
}