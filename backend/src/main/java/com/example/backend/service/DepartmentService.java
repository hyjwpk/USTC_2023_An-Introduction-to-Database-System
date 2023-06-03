package com.example.backend.service;

import com.example.backend.common.MyException;
import com.example.backend.common.Response;
import com.example.backend.common.ResponseEnum;
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

    public Response edit(Department department) {
        try {
            departmentMapper.edit(department);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response add(Department department) {
        try {
            departmentMapper.add(department);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response delete(Department department) {
        try {
            departmentMapper.delete(department);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response page(Integer page, Integer size, Department department) {
        Integer start = (page - 1) * size;
        Integer count = departmentMapper.count(department);
        List<Department> departmentList = departmentMapper.page(start, size, department);
        Map<String, Object> map = new HashMap<>();
        map.put("data", departmentList);
        map.put("count", count);
        return Response.success(map);
    }
}
