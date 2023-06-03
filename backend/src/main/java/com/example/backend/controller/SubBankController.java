package com.example.backend.controller;

import com.example.backend.common.Response;
import com.example.backend.entity.SubBank;
import com.example.backend.service.SubBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subBank")
public class SubBankController {
    final SubBankService subBankService;

    @Autowired
    public SubBankController(SubBankService subBankService) {
        this.subBankService = subBankService;
    }

    @PostMapping("/edit")
    public Response edit(@RequestBody SubBank subBank) {
        return subBankService.edit(subBank);
    }

    @PostMapping("/add")
    public Response add(@RequestBody SubBank subBank) {
        return subBankService.add(subBank);
    }

    @PostMapping("/delete")
    public Response delete(@RequestBody SubBank subBank) {
        return subBankService.delete(subBank);
    }

    @PostMapping("/page")
    public Response page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody SubBank subBank) {
        return subBankService.page(page, size, subBank);
    }
}