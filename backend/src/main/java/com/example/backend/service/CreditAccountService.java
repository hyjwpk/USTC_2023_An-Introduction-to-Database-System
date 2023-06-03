package com.example.backend.service;

import com.example.backend.entity.CreditAccount;
import com.example.backend.mapper.CreditAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CreditAccountService {
    final CreditAccountMapper creditAccountMapper;

    @Autowired
    public CreditAccountService(CreditAccountMapper creditAccountMapper) {
        this.creditAccountMapper = creditAccountMapper;
    }

    public Map<String, List<CreditAccount>> getList() {
        Map<String, List<CreditAccount>> map = new HashMap<>();
        map.put("data", creditAccountMapper.getList());
        return map;
    }

    public Map<String, String> edit(CreditAccount creditAccount) {
        Map<String, String> map = new HashMap<>();
        creditAccountMapper.edit(creditAccount);
        map.put("code", "0");
        map.put("message", "修改成功");
        return map;
    }

    public Map<String, String> add(CreditAccount creditAccount) {
        Map<String, String> map = new HashMap<>();
        creditAccountMapper.add(creditAccount);
        map.put("code", "0");
        map.put("message", "添加成功");
        return map;
    }

    public Map<String, String> delete(CreditAccount creditAccount) {
        Map<String, String> map = new HashMap<>();
        creditAccountMapper.delete(creditAccount);
        map.put("code", "0");
        map.put("message", "删除成功");
        return map;
    }

    public Map<String, Object> page(Integer page, Integer size, CreditAccount creditAccount) {
        Integer start = (page - 1) * size;
        Integer count = creditAccountMapper.count(creditAccount);
        List<CreditAccount> CreditAccountList = creditAccountMapper.page(start, size, creditAccount);
        Map<String, Object> map = new HashMap<>();
        map.put("data", CreditAccountList);
        map.put("count", count);
        return map;
    }
}
