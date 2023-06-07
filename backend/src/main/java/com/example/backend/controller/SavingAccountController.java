package com.example.backend.controller;

import com.example.backend.common.Response;
import com.example.backend.entity.SavingAccount;
import com.example.backend.dto.SavingInteract;
import com.example.backend.service.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/savingAccount")
public class SavingAccountController {
    final SavingAccountService savingAccountService;

    @Autowired
    public SavingAccountController(SavingAccountService savingAccountService) {
        this.savingAccountService = savingAccountService;
    }

    @PostMapping("/edit")
    public Response edit(@RequestBody SavingAccount savingAccount) {
        return savingAccountService.edit(savingAccount);
    }

    @PostMapping("/add")
    public Response add(@RequestBody SavingAccount savingAccount) {
        return savingAccountService.add(savingAccount);
    }

    @PostMapping("/delete")
    public Response delete(@RequestBody SavingAccount savingAccount) {
        return savingAccountService.delete(savingAccount);
    }

    @PostMapping("/page")
    public Response page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody SavingAccount savingAccount) {
        return savingAccountService.page(page, size, savingAccount);
    }

    @PostMapping("/saving")
    public Response saving(@RequestBody SavingInteract savingInteract) {
        return savingAccountService.saving(savingInteract);
    }

    @PostMapping("/withdraw")
    public Response withdraw(@RequestBody SavingInteract savingInteract) {
        return savingAccountService.withdraw(savingInteract);
    }
}