package com.example.backend.service;

import com.example.backend.common.MyException;
import com.example.backend.common.Response;
import com.example.backend.common.ResponseEnum;
import com.example.backend.entity.Member;
import com.example.backend.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberService {
    final MemberMapper memberMapper;

    @Autowired
    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public Response edit(Member member) {
        Integer status;
        Map<String, Object> map = new HashMap<>();
        try {
            memberMapper.edit(member, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == 2) {
            return new Response(ResponseEnum.FAIL.getCode(), "不存在此银行", null);
        }
        if (status == 3) {
            return new Response(ResponseEnum.FAIL.getCode(), "管理部门与所属部门不一致", null);
        }
        if (status == 4) {
            return new Response(ResponseEnum.FAIL.getCode(), "不存在此部门", null);
        }
        if (status == 5) {
            return new Response(ResponseEnum.FAIL.getCode(), "该支行不存在此部门", null);
        }
        if (status == 6) {
            return new Response(ResponseEnum.FAIL.getCode(), "工资与等级不能为负数", null);
        }
        if (status == 7) {
            return new Response(ResponseEnum.FAIL.getCode(), "手机号错误", null);
        }
        return Response.success();
    }

    public Response add(Member member) {
        Integer status;
        Map<String, Object> map = new HashMap<>();
        try {
            memberMapper.add(member, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == 2) {
            return new Response(ResponseEnum.FAIL.getCode(), "身份证号错误", null);
        }
        if (status == 3) {
            return new Response(ResponseEnum.FAIL.getCode(), "请正确填写性别：M代表男，W代表女", null);
        }
        if (status == 4) {
            return new Response(ResponseEnum.FAIL.getCode(), "手机号错误", null);
        }
        if (status == 5) {
            return new Response(ResponseEnum.FAIL.getCode(), "请填写正确的银行与部门", null);
        }
        return Response.success();
    }

    public Response delete(Member member) {
        try {
            memberMapper.delete(member);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response page(Integer page, Integer size, Member member) {
        Integer start = (page - 1) * size;
        Integer count = memberMapper.count(member);
        List<Member> memberList = memberMapper.page(start, size, member);
        Map<String, Object> map = new HashMap<>();
        map.put("data", memberList);
        map.put("count", count);
        return Response.success(map);
    }
}
