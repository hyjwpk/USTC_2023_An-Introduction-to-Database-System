package com.example.backend.service;

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

    public Map<String, List<Member>> getList() {
        Map<String, List<Member>> map = new HashMap<>();
        map.put("data", memberMapper.getList());
        return map;
    }

    public Map<String, String> edit(Member member) {
        Map<String, String> map = new HashMap<>();
        memberMapper.edit(member);
        map.put("code", "0");
        map.put("message", "修改成功");
        return map;
    }

    public Map<String, String> add(Member member) {
        Map<String, String> map = new HashMap<>();
        memberMapper.add(member);
        map.put("code", "0");
        map.put("message", "添加成功");
        return map;
    }

    public Map<String, String> delete(Member member) {
        Map<String, String> map = new HashMap<>();
        memberMapper.delete(member);
        map.put("code", "0");
        map.put("message", "删除成功");
        return map;
    }

    public Map<String, Object> page(Integer page, Integer size, Member member) {
        Integer start = (page - 1) * size;
        Integer count = memberMapper.count(member);
        List<Member> MemberList = memberMapper.page(start, size, member);
        Map<String, Object> map = new HashMap<>();
        map.put("data", MemberList);
        map.put("count", count);
        return map;
    }
}