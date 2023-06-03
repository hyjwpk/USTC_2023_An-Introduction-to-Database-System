package com.example.backend.controller;

import com.example.backend.common.Response;
import com.example.backend.entity.Client;
import com.example.backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/edit")
    public Response edit(@RequestBody Client client) {
        return clientService.edit(client);
    }

    @PostMapping("/add")
    public Response add(@RequestBody Client client) {
        return clientService.add(client);
    }

    @PostMapping("/delete")
    public Response delete(@RequestBody Client client) {
        return clientService.delete(client);
    }

    @PostMapping("/page")
    public Response page(@RequestParam Integer page, @RequestParam Integer size, @RequestBody Client client) {
        return clientService.page(page, size, client);
    }
}