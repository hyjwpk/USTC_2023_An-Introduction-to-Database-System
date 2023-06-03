package com.example.backend.service;

import com.example.backend.common.MyException;
import com.example.backend.common.Response;
import com.example.backend.common.ResponseEnum;
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

    public Response add(Serve serve) {
        try {
            serveMapper.add(serve);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response delete(Serve serve) {
        try {
            serveMapper.delete(serve);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response page(Integer page, Integer size, Serve serve) {
        Integer start = (page - 1) * size;
        Integer count = serveMapper.count(serve);
        List<Serve> serveList = serveMapper.page(start, size, serve);
        Map<String, Object> map = new HashMap<>();
        map.put("data", serveList);
        map.put("count", count);
        return Response.success(map);
    }
}
