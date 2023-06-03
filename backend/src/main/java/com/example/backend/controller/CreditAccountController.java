package com.example.backend.controller;

import com.example.backend.common.Response;
import com.example.backend.entity.CreditAccount;
import com.example.backend.service.CreditAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/creditAccount")
public class CreditAccountController {
    final CreditAccountService creditAccountService;

    @Autowired
    public CreditAccountController(CreditAccountService creditAccountService) {
        this.creditAccountService = creditAccountService;
    }

    @PostMapping("/edit")
    public Response edit(@RequestBody CreditAccount creditAccount) {
        return creditAccountService.edit(creditAccount);
    }

    @PostMapping("/add")
    public Response add(@RequestBody CreditAccount creditAccount) {
        return creditAccountService.add(creditAccount);
    }

    @PostMapping("/delete")
    public Response delete(@RequestBody CreditAccount creditAccount) {
        return creditAccountService.delete(creditAccount);
    }

    @PostMapping("/page")
    public Response page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody CreditAccount creditAccount) {
        return creditAccountService.page(page, size, creditAccount);
    }
}