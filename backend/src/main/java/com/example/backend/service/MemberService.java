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
        try {
            memberMapper.edit(member);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response add(Member member) {
        try {
            memberMapper.add(member);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
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
