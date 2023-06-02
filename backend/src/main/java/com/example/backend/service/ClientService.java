package com.example.backend.service;

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
    public ClientService(ClientMapper bookMapper) {
        this.clientMapper = bookMapper;
    }

    public Map<String, List<Client>> getList() {
        Map<String, List<Client>> map = new HashMap<>();
        map.put("data", clientMapper.getList());
        return map;
    }

    public Map<String, String> edit(Client client) {
        Map<String, String> map = new HashMap<>();
        clientMapper.edit(client);
        map.put("code", "0");
        map.put("message", "修改成功");
        return map;
    }

    public Map<String, String> add(Client client) {
        Map<String, String> map = new HashMap<>();
        clientMapper.add(client);
        map.put("code", "0");
        map.put("message", "添加成功");
        return map;
    }

    public Map<String, String> delete(Client client) {
        Map<String, String> map = new HashMap<>();
        clientMapper.delete(client);
        map.put("code", "0");
        map.put("message", "删除成功");
        return map;
    }

    public Map<String, Object> page(Integer page, Integer size, String client_ID, String real_name, String client_phone, String client_address, String client_email) {
        Integer start = (page - 1) * size;
        Integer count = clientMapper.count(client_ID, real_name, client_phone, client_address, client_email);
        List<Client> ClientList = clientMapper.page(start, size, client_ID, real_name, client_phone, client_address, client_email);
        Map<String, Object> map = new HashMap<>();
        map.put("data", ClientList);
        map.put("count", count);
        return map;
    }
}
