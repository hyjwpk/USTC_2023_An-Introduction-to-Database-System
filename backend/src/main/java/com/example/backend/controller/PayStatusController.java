package com.example.backend.controller;

import com.example.backend.common.Response;
import com.example.backend.entity.PayStatus;
import com.example.backend.service.PayStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payStatus")
public class PayStatusController {
    final PayStatusService payStatusService;

    @Autowired
    public PayStatusController(PayStatusService payStatusService) {
        this.payStatusService = payStatusService;
    }

    @PostMapping("/edit")
    public Response edit(@RequestBody PayStatus payStatus) {
        return payStatusService.edit(payStatus);
    }

    @PostMapping("/add")
    public Response add(@RequestBody PayStatus payStatus) {
        return payStatusService.add(payStatus);
    }

    @PostMapping("/delete")
    public Response delete(@RequestBody PayStatus payStatus) {
        return payStatusService.delete(payStatus);
    }

    @PostMapping("/page")
    public Response page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody PayStatus payStatus) {
        return payStatusService.page(page, size, payStatus);
    }
}