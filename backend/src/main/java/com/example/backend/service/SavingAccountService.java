package com.example.backend.service;

import com.example.backend.common.MyException;
import com.example.backend.common.Response;
import com.example.backend.common.ResponseEnum;
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

    public Response edit(SavingAccount savingAccount) {
        try {
            savingAccountMapper.edit(savingAccount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response add(SavingAccount savingAccount) {
        try {
            savingAccountMapper.add(savingAccount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response delete(SavingAccount savingAccount) {
        try {
            savingAccountMapper.delete(savingAccount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response page(Integer page, Integer size, SavingAccount savingAccount) {
        Integer start = (page - 1) * size;
        Integer count = savingAccountMapper.count(savingAccount);
        List<SavingAccount> savingAccountList = savingAccountMapper.page(start, size, savingAccount);
        Map<String, Object> map = new HashMap<>();
        map.put("data", savingAccountList);
        map.put("count", count);
        return Response.success(map);
    }
}
