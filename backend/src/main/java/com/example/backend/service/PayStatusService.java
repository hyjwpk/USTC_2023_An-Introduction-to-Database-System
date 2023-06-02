package com.example.backend.service;

import com.example.backend.entity.PayStatus;
import com.example.backend.mapper.PayStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayStatusService {
    final PayStatusMapper payStatusMapper;

    @Autowired
    public PayStatusService(PayStatusMapper payStatusMapper) {
        this.payStatusMapper = payStatusMapper;
    }

    public Map<String, List<PayStatus>> getList() {
        Map<String, List<PayStatus>> map = new HashMap<>();
        map.put("data", payStatusMapper.getList());
        return map;
    }

    public Map<String, String> edit(PayStatus payStatus) {
        Map<String, String> map = new HashMap<>();
        payStatusMapper.edit(payStatus);
        map.put("code", "0");
        map.put("message", "修改成功");
        return map;
    }

    public Map<String, String> add(PayStatus payStatus) {
        Map<String, String> map = new HashMap<>();
        payStatusMapper.add(payStatus);
        map.put("code", "0");
        map.put("message", "添加成功");
        return map;
    }

    public Map<String, String> delete(PayStatus payStatus) {
        Map<String, String> map = new HashMap<>();
        payStatusMapper.delete(payStatus);
        map.put("code", "0");
        map.put("message", "删除成功");
        return map;
    }

    public Map<String, Object> page(Integer page, Integer size, PayStatus payStatus) {
        Integer start = (page - 1) * size;
        Integer count = payStatusMapper.count(payStatus);
        List<PayStatus> PayStatusList = payStatusMapper.page(start, size, payStatus);
        Map<String, Object> map = new HashMap<>();
        map.put("data", PayStatusList);
        map.put("count", count);
        return map;
    }
}
