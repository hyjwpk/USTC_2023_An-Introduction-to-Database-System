package com.example.backend.controller;

import com.example.backend.entity.SubBank;
import com.example.backend.service.SubBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subBank")
public class SubBankController {
    final SubBankService subBankService;

    @Autowired
    public SubBankController(SubBankService subBankService) {
        this.subBankService = subBankService;
    }


    @GetMapping("/getList")
    public Map<String, List<SubBank>> getSubBankList() {
        return subBankService.getList();
    }

    @PostMapping("/edit")
    public Map<String, String> editSubBank(@RequestBody SubBank subBank) {
        return subBankService.edit(subBank);
    }

    @PostMapping("/add")
    public Map<String, String> addSubBank(@RequestBody SubBank subBank) {
        return subBankService.add(subBank);
    }

    @PostMapping("/delete")
    public Map<String, String> deleteSubBank(@RequestBody SubBank subBank) {
        return subBankService.delete(subBank);
    }

    @PostMapping("/page")
    public Map<String, Object> page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody SubBank subBank) {
        return subBankService.page(page, size, subBank);
    }
}

