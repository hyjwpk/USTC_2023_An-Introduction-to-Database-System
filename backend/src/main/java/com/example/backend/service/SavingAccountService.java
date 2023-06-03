package com.example.backend.service;

import com.example.backend.entity.SavingAccount;
import com.example.backend.mapper.SavingAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SavingAccountService {
    final SavingAccountMapper savingAccountMapper;

    @Autowired
    public SavingAccountService(SavingAccountMapper savingAccountMapper) {
        this.savingAccountMapper = savingAccountMapper;
    }

    public Map<String, List<SavingAccount>> getList() {
        Map<String, List<SavingAccount>> map = new HashMap<>();
        map.put("data", savingAccountMapper.getList());
        return map;
    }

    public Map<String, String> edit(SavingAccount savingAccount) {
        Map<String, String> map = new HashMap<>();
        savingAccountMapper.edit(savingAccount);
        map.put("code", "0");
        map.put("message", "修改成功");
        return map;
    }

    public Map<String, String> add(SavingAccount savingAccount) {
        Map<String, String> map = new HashMap<>();
        savingAccountMapper.add(savingAccount);
        map.put("code", "0");
        map.put("message", "添加成功");
        return map;
    }

    public Map<String, String> delete(SavingAccount savingAccount) {
        Map<String, String> map = new HashMap<>();
        savingAccountMapper.delete(savingAccount);
        map.put("code", "0");
        map.put("message", "删除成功");
        return map;
    }

    public Map<String, Object> page(Integer page, Integer size, SavingAccount savingAccount) {
        Integer start = (page - 1) * size;
        Integer count = savingAccountMapper.count(savingAccount);
        List<SavingAccount> SavingAccountList = savingAccountMapper.page(start, size, savingAccount);
        Map<String, Object> map = new HashMap<>();
        map.put("data", SavingAccountList);
        map.put("count", count);
        return map;
    }
}
