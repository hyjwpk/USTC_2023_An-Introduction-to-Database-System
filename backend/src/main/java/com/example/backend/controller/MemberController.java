package com.example.backend.controller;

import com.example.backend.entity.Member;
import com.example.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/getList")
    public Map<String, List<Member>> getMemberList() {
        return memberService.getList();
    }

    @PostMapping("/edit")
    public Map<String, String> editMember(@RequestBody Member member) {
        return memberService.edit(member);
    }

    @PostMapping("/add")
    public Map<String, String> addMember(@RequestBody Member member) {
        return memberService.add(member);
    }

    @PostMapping("/delete")
    public Map<String, String> deleteMember(@RequestBody Member member) {
        return memberService.delete(member);
    }

    @PostMapping("/page")
    public Map<String, Object> page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody Member member) {
        return memberService.page(page, size, member);
    }
}

