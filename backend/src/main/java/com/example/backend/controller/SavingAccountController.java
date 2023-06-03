package com.example.backend.controller;

import com.example.backend.entity.SavingAccount;
import com.example.backend.service.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/savingAccount")
public class SavingAccountController {
    final SavingAccountService savingAccountService;

    @Autowired
    public SavingAccountController(SavingAccountService savingAccountService) {
        this.savingAccountService = savingAccountService;
    }


    @GetMapping("/getList")
    public Map<String, List<SavingAccount>> getSavingAccountList() {
        return savingAccountService.getList();
    }

    @PostMapping("/edit")
    public Map<String, String> editSavingAccount(@RequestBody SavingAccount savingAccount) {
        return savingAccountService.edit(savingAccount);
    }

    @PostMapping("/add")
    public Map<String, String> addSavingAccount(@RequestBody SavingAccount savingAccount) {
        return savingAccountService.add(savingAccount);
    }

    @PostMapping("/delete")
    public Map<String, String> deleteSavingAccount(@RequestBody SavingAccount savingAccount) {
        return savingAccountService.delete(savingAccount);
    }

    @PostMapping("/page")
    public Map<String, Object> page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody SavingAccount savingAccount) {
        return savingAccountService.page(page, size, savingAccount);
    }
}

