package com.example.backend.controller;

import com.example.backend.entity.Serve;
import com.example.backend.service.ServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/serve")
public class ServeController {
    final ServeService serveService;

    @Autowired
    public ServeController(ServeService serveService) {
        this.serveService = serveService;
    }


    @GetMapping("/getList")
    public Map<String, List<Serve>> getServeList() {
        return serveService.getList();
    }

    @PostMapping("/add")
    public Map<String, String> addServe(@RequestBody Serve serve) {
        return serveService.add(serve);
    }

    @PostMapping("/delete")
    public Map<String, String> deleteServe(@RequestBody Serve serve) {
        return serveService.delete(serve);
    }

    @PostMapping("/page")
    public Map<String, Object> page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody Serve serve) {
        return serveService.page(page, size, serve);
    }
}

