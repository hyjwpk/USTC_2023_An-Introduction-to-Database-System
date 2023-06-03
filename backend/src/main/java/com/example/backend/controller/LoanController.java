package com.example.backend.controller;

import com.example.backend.common.Response;
import com.example.backend.entity.Loan;
import com.example.backend.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
public class LoanController {
    final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/edit")
    public Response edit(@RequestBody Loan loan) {
        return loanService.edit(loan);
    }

    @PostMapping("/add")
    public Response add(@RequestBody Loan loan) {
        return loanService.add(loan);
    }

    @PostMapping("/delete")
    public Response delete(@RequestBody Loan loan) {
        return loanService.delete(loan);
    }

    @PostMapping("/page")
    public Response page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody Loan loan) {
        return loanService.page(page, size, loan);
    }
}