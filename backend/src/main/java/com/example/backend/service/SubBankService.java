package com.example.backend.service;

import com.example.backend.entity.SubBank;
import com.example.backend.mapper.SubBankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubBankService {
    final SubBankMapper subBankMapper;

    @Autowired
    public SubBankService(SubBankMapper subBankMapper) {
        this.subBankMapper = subBankMapper;
    }

    public Map<String, List<SubBank>> getList() {
        Map<String, List<SubBank>> map = new HashMap<>();
        map.put("data", subBankMapper.getList());
        return map;
    }

    public Map<String, String> edit(SubBank subBank) {
        Map<String, String> map = new HashMap<>();
        subBankMapper.edit(subBank);
        map.put("code", "0");
        map.put("message", "修改成功");
        return map;
    }

    public Map<String, String> add(SubBank subBank) {
        Map<String, String> map = new HashMap<>();
        subBankMapper.add(subBank);
        map.put("code", "0");
        map.put("message", "添加成功");
        return map;
    }

    public Map<String, String> delete(SubBank subBank) {
        Map<String, String> map = new HashMap<>();
        subBankMapper.delete(subBank);
        map.put("code", "0");
        map.put("message", "删除成功");
        return map;
    }

    public Map<String, Object> page(Integer page, Integer size, SubBank subBank) {
        Integer start = (page - 1) * size;
        Integer count = subBankMapper.count(subBank);
        List<SubBank> SubBankList = subBankMapper.page(start, size, subBank);
        Map<String, Object> map = new HashMap<>();
        map.put("data", SubBankList);
        map.put("count", count);
        return map;
    }
}
