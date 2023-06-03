package com.example.backend.controller;

import com.example.backend.entity.CreditAccount;
import com.example.backend.service.CreditAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/creditAccount")
public class CreditAccountController {
    final CreditAccountService creditAccountService;

    @Autowired
    public CreditAccountController(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }


    @GetMapping("/getList")
    public Map<String, List<CreditAccount>> getCreditAccountList() {
        return creditAccountService.getList();
    }

    @PostMapping("/edit")
    public Map<String, String> editCreditAccount(@RequestBody CreditAccount creditAccount) {
        return creditAccountService.edit(creditAccount);
    }

    @PostMapping("/add")
    public Map<String, String> addCreditAccount(@RequestBody CreditAccount creditAccount) {
        return creditAccountService.add(creditAccount);
    }

    @PostMapping("/delete")
    public Map<String, String> deleteCreditAccount(@RequestBody CreditAccount creditAccount) {
        return creditAccountService.delete(creditAccount);
    }

    @PostMapping("/page")
    public Map<String, Object> page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody CreditAccount creditAccount) {
        return creditAccountService.page(page, size, creditAccount);
    }
}

