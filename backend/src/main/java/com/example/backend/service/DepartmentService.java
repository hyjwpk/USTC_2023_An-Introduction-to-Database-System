package com.example.backend.service;

import com.example.backend.entity.Department;
import com.example.backend.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService {
    final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    public Map<String, List<Department>> getList() {
        Map<String, List<Department>> map = new HashMap<>();
        map.put("data", departmentMapper.getList());
        return map;
    }

    public Map<String, String> edit(Department department) {
        Map<String, String> map = new HashMap<>();
        departmentMapper.edit(department);
        map.put("code", "0");
        map.put("message", "修改成功");
        return map;
    }

    public Map<String, String> add(Department department) {
        Map<String, String> map = new HashMap<>();
        departmentMapper.add(department);
        map.put("code", "0");
        map.put("message", "添加成功");
        return map;
    }

    public Map<String, String> delete(Department department) {
        Map<String, String> map = new HashMap<>();
        departmentMapper.delete(department);
        map.put("code", "0");
        map.put("message", "删除成功");
        return map;
    }

    public Map<String, Object> page(Integer page, Integer size, Department department) {
        Integer start = (page - 1) * size;
        Integer count = departmentMapper.count(department);
        List<Department> DepartmentList = departmentMapper.page(start, size, department);
        Map<String, Object> map = new HashMap<>();
        map.put("data", DepartmentList);
        map.put("count", count);
        return map;
    }
}
