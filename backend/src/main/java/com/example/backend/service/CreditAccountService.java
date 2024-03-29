package com.example.backend.service;

import com.example.backend.common.MyException;
import com.example.backend.common.Response;
import com.example.backend.common.ResponseEnum;
import com.example.backend.entity.CreditAccount;
import com.example.backend.dto.SavingInteract;
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
        Integer status;
        Map<String, Object> map = new HashMap<>();
        try {
            creditAccountMapper.add(creditAccount, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == 2) {
            return new Response(ResponseEnum.FAIL.getCode(), "已在本支行办理过信用卡", null);
        }
        if (status == 3) {
            return new Response(ResponseEnum.FAIL.getCode(), "请检查是否已经注册", null);
        }
        if (status == 4) {
            return new Response(ResponseEnum.FAIL.getCode(), "请检查支行名称是否正确", null);
        }
        if (status == 5) {
            return new Response(ResponseEnum.FAIL.getCode(), "额度不能为负数", null);
        }
        return Response.success();
    }

    public Response delete(CreditAccount creditAccount) {
        Integer status;
        Map<String, Object> map = new HashMap<>();
        try {
            creditAccountMapper.delete(creditAccount, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == 2) {
            return new Response(ResponseEnum.FAIL.getCode(), "请还清信用卡的使用金额", null);
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

    public Response return_c(SavingInteract savingInteract) {
        Integer status;
        Map<String, Object> map = new HashMap<>();
        try {
            creditAccountMapper.return_c(savingInteract, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == -2) {
            return new Response(ResponseEnum.FAIL.getCode(), "本储蓄卡不存在", null);
        }
        if (status == 0) {
            return new Response(ResponseEnum.FAIL.getCode(), "该信用卡已还清", null);
        }
        if (status == -5) {
            return new Response(ResponseEnum.FAIL.getCode(), "金额不能为负数", null);
        }
        if (status > 0) {
            return new Response(ResponseEnum.FAIL.getCode(), "该信用卡只需还" + status + "元即可", null);
        }
        return Response.success();
    }

    public Response lend(SavingInteract savingInteract) {
        Integer status;
        Map<String, Object> map = new HashMap<>();
        try {
            creditAccountMapper.lend(savingInteract, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == -2) {
            return new Response(ResponseEnum.FAIL.getCode(), "本储蓄卡不存在", null);
        }
        if (status == -3) {
            return new Response(ResponseEnum.FAIL.getCode(), "超过本卡额度", null);
        }
        if (status == -4) {
            return new Response(ResponseEnum.FAIL.getCode(), "暂时无法使用这么多钱", null);
        }
        if (status == -5) {
            return new Response(ResponseEnum.FAIL.getCode(), "金额不能为负数", null);
        }
        return Response.success();
    }
}
