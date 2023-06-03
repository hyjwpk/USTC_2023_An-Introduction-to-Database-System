package com.example.backend.controller;

import com.example.backend.common.Response;
import com.example.backend.entity.Department;
import com.example.backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/edit")
    public Response edit(@RequestBody Department department) {
        return departmentService.edit(department);
    }

    @PostMapping("/add")
    public Response add(@RequestBody Department department) {
        return departmentService.add(department);
    }

    @PostMapping("/delete")
    public Response delete(@RequestBody Department department) {
        return departmentService.delete(department);
    }

    @PostMapping("/page")
    public Response page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody Department department) {
        return departmentService.page(page, size, department);
    }
}