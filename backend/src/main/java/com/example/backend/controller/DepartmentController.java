package com.example.backend.controller;

import com.example.backend.entity.Department;
import com.example.backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @GetMapping("/getList")
    public Map<String, List<Department>> getDepartmentList() {
        return departmentService.getList();
    }

    @PostMapping("/edit")
    public Map<String, String> editDepartment(@RequestBody Department department) {
        return departmentService.edit(department);
    }

    @PostMapping("/add")
    public Map<String, String> addDepartment(@RequestBody Department department) {
        return departmentService.add(department);
    }

    @PostMapping("/delete")
    public Map<String, String> deleteDepartment(@RequestBody Department department) {
        return departmentService.delete(department);
    }

    @PostMapping("/page")
    public Map<String, Object> page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody Department department) {
        return departmentService.page(page, size, department);
    }
}

