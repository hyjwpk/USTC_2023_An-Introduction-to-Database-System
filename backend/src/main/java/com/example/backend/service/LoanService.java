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
        Integer status;
        Map<String,Object> map = new HashMap<>();
        try {
            loanMapper.add(loan, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == 2){
            return new Response(ResponseEnum.FAIL.getCode(),"请检查是否已注册", null);
        }
        if (status == 3){
            return new Response(ResponseEnum.FAIL.getCode(),"请先还已借贷款", null);
        }
        if (status == 4){
            return new Response(ResponseEnum.FAIL.getCode(),"贷款金额过多", null);
        }
        if (status == 5){
            return new Response(ResponseEnum.FAIL.getCode(),"请检查支行填写是否正确", null);
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
