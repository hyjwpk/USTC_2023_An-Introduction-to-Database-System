package com.example.backend.controller;

import com.example.backend.entity.PayStatus;
import com.example.backend.service.PayStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payStatus")
public class PayStatusController {
    final PayStatusService payStatusService;

    @Autowired
    public PayStatusController(PayStatusService payStatusService) {
        this.payStatusService = payStatusService;
    }


    @GetMapping("/getList")
    public Map<String, List<PayStatus>> getPayStatusList() {
        return payStatusService.getList();
    }

    @PostMapping("/edit")
    public Map<String, String> editPayStatus(@RequestBody PayStatus payStatus) {
        return payStatusService.edit(payStatus);
    }

    @PostMapping("/add")
    public Map<String, String> addPayStatus(@RequestBody PayStatus payStatus) {
        return payStatusService.add(payStatus);
    }

    @PostMapping("/delete")
    public Map<String, String> deletePayStatus(@RequestBody PayStatus payStatus) {
        return payStatusService.delete(payStatus);
    }

    @PostMapping("/page")
    public Map<String, Object> page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody PayStatus payStatus) {
        return payStatusService.page(page, size, payStatus);
    }
}

