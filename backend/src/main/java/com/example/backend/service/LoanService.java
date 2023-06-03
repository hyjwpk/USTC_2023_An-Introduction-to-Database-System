package com.example.backend.service;

import com.example.backend.common.MyException;
import com.example.backend.common.Response;
import com.example.backend.common.ResponseEnum;
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

    public Response edit(Loan loan) {
        try {
            loanMapper.edit(loan);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response add(Loan loan) {
        try {
            loanMapper.add(loan);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response delete(Loan loan) {
        try {
            loanMapper.delete(loan);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response page(Integer page, Integer size, Loan loan) {
        Integer start = (page - 1) * size;
        Integer count = loanMapper.count(loan);
        List<Loan> loanList = loanMapper.page(start, size, loan);
        Map<String, Object> map = new HashMap<>();
        map.put("data", loanList);
        map.put("count", count);
        return Response.success(map);
    }
}
