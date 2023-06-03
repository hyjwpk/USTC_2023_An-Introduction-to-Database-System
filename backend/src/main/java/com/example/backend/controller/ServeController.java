package com.example.backend.controller;

import com.example.backend.common.Response;
import com.example.backend.entity.Serve;
import com.example.backend.service.ServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/serve")
public class ServeController {
    final ServeService serveService;

    @Autowired
    public ServeController(ServeService serveService) {
        this.serveService = serveService;
    }

    @PostMapping("/add")
    public Response add(@RequestBody Serve serve) {
        return serveService.add(serve);
    }

    @PostMapping("/delete")
    public Response delete(@RequestBody Serve serve) {
        return serveService.delete(serve);
    }

    @PostMapping("/page")
    public Response page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody Serve serve) {
        return serveService.page(page, size, serve);
    }
}