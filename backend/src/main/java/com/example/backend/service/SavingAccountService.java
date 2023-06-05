package com.example.backend.service;

import com.example.backend.common.MyException;
import com.example.backend.common.Response;
import com.example.backend.common.ResponseEnum;
import com.example.backend.entity.CreditAccount;
import com.example.backend.entity.SavingAccount;
import com.example.backend.entity.SavingInteract;
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
        Integer status;
        Map<String,Object> map = new HashMap<>();
        try {
            savingAccountMapper.add(savingAccount, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == 2){
            return new Response(ResponseEnum.FAIL.getCode(),"已在本支行办理过储蓄卡", null);
        }
        if (status == 3){
            return new Response(ResponseEnum.FAIL.getCode(),"请检查是否已经注册", null);
        }
        if (status == 4){
            return new Response(ResponseEnum.FAIL.getCode(),"请检查支行名称是否正确", null);
        }
        if (status == 18){
            return new Response(ResponseEnum.FAIL.getCode(),"身份证号错误", null);
        }
        return new Response(ResponseEnum.SUCCESS.getCode(),"申请储蓄卡成功", null);
    }

    public Response saving(SavingInteract savingInteract) {
        Integer status;
        Map<String,Object> map = new HashMap<>();
        try {
            savingAccountMapper.saving(savingInteract, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == 2){
            return new Response(ResponseEnum.FAIL.getCode(),"本储蓄卡不存在", null);
        }
        if (status == 3){
            return new Response(ResponseEnum.FAIL.getCode(),"余额不足", null);
        }
        return new Response(ResponseEnum.SUCCESS.getCode(),"交互成功", null);
    }

    public Response withdraw(SavingInteract savingInteract) {
        Integer status;
        Map<String,Object> map = new HashMap<>();
        try {
            savingAccountMapper.withdraw(savingInteract, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == 2){
            return new Response(ResponseEnum.FAIL.getCode(),"本储蓄卡不存在", null);
        }
        if (status == 3){
            return new Response(ResponseEnum.FAIL.getCode(),"余额不足", null);
        }
        return new Response(ResponseEnum.SUCCESS.getCode(),"交互成功", null);
    }
    public Response delete(SavingAccount savingAccount) {
        Integer status;
        Map<String,Object> map = new HashMap<>();
        try {
            savingAccountMapper.delete(savingAccount, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == 2) {
            return new Response(ResponseEnum.FAIL.getCode(), "本卡中还有现金未取出", null);
        }
        return new Response(ResponseEnum.SUCCESS.getCode(),"销毁储蓄卡成功", null);
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
