package com.example.backend.controller;

import com.example.backend.entity.Loan;
import com.example.backend.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/loan")
public class LoanController {
    final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }


    @GetMapping("/getList")
    public Map<String, List<Loan>> getLoanList() {
        return loanService.getList();
    }

    @PostMapping("/edit")
    public Map<String, String> editLoan(@RequestBody Loan loan) {
        return loanService.edit(loan);
    }

    @PostMapping("/add")
    public Map<String, String> addLoan(@RequestBody Loan loan) {
        return loanService.add(loan);
    }

    @PostMapping("/delete")
    public Map<String, String> deleteLoan(@RequestBody Loan loan) {
        return loanService.delete(loan);
    }

    @PostMapping("/page")
    public Map<String, Object> page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody Loan loan) {
        return loanService.page(page, size, loan);
    }
}
