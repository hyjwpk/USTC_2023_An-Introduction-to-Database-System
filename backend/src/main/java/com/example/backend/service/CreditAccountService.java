package com.example.backend.service;

import com.example.backend.common.MyException;
import com.example.backend.common.Response;
import com.example.backend.common.ResponseEnum;
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

    public Response edit(CreditAccount creditAccount) {
        try {
            creditAccountMapper.edit(creditAccount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response add(CreditAccount creditAccount) {
        try {
            creditAccountMapper.add(creditAccount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response delete(CreditAccount creditAccount) {
        try {
            creditAccountMapper.delete(creditAccount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response page(Integer page, Integer size, CreditAccount creditAccount) {
        Integer start = (page - 1) * size;
        Integer count = creditAccountMapper.count(creditAccount);
        List<CreditAccount> creditAccountList = creditAccountMapper.page(start, size, creditAccount);
        Map<String, Object> map = new HashMap<>();
        map.put("data", creditAccountList);
        map.put("count", count);
        return Response.success(map);
    }
}
