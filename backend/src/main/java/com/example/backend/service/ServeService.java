package com.example.backend.service;

import com.example.backend.entity.Serve;
import com.example.backend.mapper.ServeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServeService {
    final ServeMapper serveMapper;

    @Autowired
    public ServeService(ServeMapper serveMapper) {
        this.serveMapper = serveMapper;
    }

    public Map<String, List<Serve>> getList() {
        Map<String, List<Serve>> map = new HashMap<>();
        map.put("data", serveMapper.getList());
        return map;
    }


    public Map<String, String> add(Serve serve) {
        Map<String, String> map = new HashMap<>();
        serveMapper.add(serve);
        map.put("code", "0");
        map.put("message", "添加成功");
        return map;
    }

    public Map<String, String> delete(Serve serve) {
        Map<String, String> map = new HashMap<>();
        serveMapper.delete(serve);
        map.put("code", "0");
        map.put("message", "删除成功");
        return map;
    }

    public Map<String, Object> page(Integer page, Integer size, Serve serve) {
        Integer start = (page - 1) * size;
        Integer count = serveMapper.count(serve);
        List<Serve> ServeList = serveMapper.page(start, size, serve);
        Map<String, Object> map = new HashMap<>();
        map.put("data", ServeList);
        map.put("count", count);
        return map;
    }
}
