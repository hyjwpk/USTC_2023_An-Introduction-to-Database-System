package com.example.backend.service;

import com.example.backend.entity.Loan;
import com.example.backend.mapper.LoanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoanService {
    final LoanMapper loanMapper;

    @Autowired
    public LoanService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    public Map<String, List<Loan>> getList() {
        Map<String, List<Loan>> map = new HashMap<>();
        map.put("data", loanMapper.getList());
        return map;
    }

    public Map<String, String> edit(Loan loan) {
        Map<String, String> map = new HashMap<>();
        loanMapper.edit(loan);
        map.put("code", "0");
        map.put("message", "修改成功");
        return map;
    }

    public Map<String, String> add(Loan loan) {
        Map<String, String> map = new HashMap<>();
        loanMapper.add(loan);
        map.put("code", "0");
        map.put("message", "添加成功");
        return map;
    }

    public Map<String, String> delete(Loan loan) {
        Map<String, String> map = new HashMap<>();
        loanMapper.delete(loan);
        map.put("code", "0");
        map.put("message", "删除成功");
        return map;
    }

    public Map<String, Object> page(Integer page, Integer size, Loan loan) {
        Integer start = (page - 1) * size;
        Integer count = loanMapper.count(loan);
        List<Loan> LoanList = loanMapper.page(start, size, loan);
        Map<String, Object> map = new HashMap<>();
        map.put("data", LoanList);
        map.put("count", count);
        return map;
    }
}
