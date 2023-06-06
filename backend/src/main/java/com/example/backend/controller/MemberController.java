package com.example.backend.controller;

import com.example.backend.common.Response;
import com.example.backend.entity.EditMember;
import com.example.backend.entity.Member;
import com.example.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/edit")
    public Response edit(@RequestBody EditMember editmember) {
        return memberService.edit(editmember);
    }

    @PostMapping("/add")
    public Response add(@RequestBody Member member) {
        return memberService.add(member);
    }

    @PostMapping("/delete")
    public Response delete(@RequestBody Member member) {
        return memberService.delete(member);
    }

    @PostMapping("/page")
    public Response page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody Member member) {
        return memberService.page(page, size, member);
    }
}