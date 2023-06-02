package com.example.backend.controller;

import com.example.backend.entity.Client;
import com.example.backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientController {
    final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/getList")
    public Map<String, List<Client>> getClientList() {
        return clientService.getList();
    }

    @PostMapping("/edit")
    public Map<String, String> editClient(@RequestBody Client client) {
        return clientService.edit(client);
    }

    @PostMapping("/add")
    public Map<String, String> addClient(@RequestBody Client client) {
        return clientService.add(client);
    }

    @PostMapping("/delete")
    public Map<String, String> deleteClient(@RequestBody Client client) {
        return clientService.delete(client);
    }

    @PostMapping("/page")
    public Map<String, Object> page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody Client client) {
        return clientService.page(page, size, client);
    }
}

