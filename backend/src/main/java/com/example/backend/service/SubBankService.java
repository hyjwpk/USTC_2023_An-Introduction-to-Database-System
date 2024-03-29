package com.example.backend.service;

import com.example.backend.common.MyException;
import com.example.backend.common.Response;
import com.example.backend.common.ResponseEnum;
import com.example.backend.dto.RenameBank;
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

    public Response edit(SubBank subBank) {
        Integer status;
        Map<String, Object> map = new HashMap<>();
        try {
            subBankMapper.edit(subBank, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == 2) {
            return new Response(ResponseEnum.FAIL.getCode(), "总资产不能为负数", null);
        }
        return Response.success();
    }


    public Response add(SubBank subBank) {
        Integer status;
        Map<String, Object> map = new HashMap<>();
        try {
            subBankMapper.add(subBank, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == 2) {
            return new Response(ResponseEnum.FAIL.getCode(), "总资产不能为负数", null);
        }
        if (status == 3) {
            return new Response(ResponseEnum.FAIL.getCode(), "该支行名称已存在", null);
        }
        return Response.success();
    }

    public Response delete(SubBank subBank) {
        try {
            subBankMapper.delete(subBank);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response page(Integer page, Integer size, SubBank subBank) {
        Integer start = (page - 1) * size;
        Integer count = subBankMapper.count(subBank);
        List<SubBank> subBankList = subBankMapper.page(start, size, subBank);
        Map<String, Object> map = new HashMap<>();
        map.put("data", subBankList);
        map.put("count", count);
        return Response.success(map);
    }

    public Response rename(RenameBank renameBank) {
        Integer status;
        Map<String, Object> map = new HashMap<>();
        try {
            subBankMapper.rename(renameBank, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == 2) {
            return new Response(ResponseEnum.FAIL.getCode(), "该名称已存在", null);
        }
        return Response.success();
    }
}
