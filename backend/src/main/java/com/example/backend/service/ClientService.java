package com.example.backend.service;

import com.example.backend.common.MyException;
import com.example.backend.common.Response;
import com.example.backend.common.ResponseEnum;
import com.example.backend.entity.Client;
import com.example.backend.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientService {
    final ClientMapper clientMapper;

    @Autowired
    public ClientService(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    public Response edit(Client client) {
        try {
            clientMapper.edit(client);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response add(Client client) {
        try {
            clientMapper.add(client);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response delete(Client client) {
        try {
            clientMapper.delete(client);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response page(Integer page, Integer size, Client client) {
        Integer start = (page - 1) * size;
        Integer count = clientMapper.count(client);
        List<Client> clientList = clientMapper.page(start, size, client);
        Map<String, Object> map = new HashMap<>();
        map.put("data", clientList);
        map.put("count", count);
        return Response.success(map);
    }
}
