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
    final
    ClientService ClientService;

    @Autowired
    public ClientController(ClientService ClientService) {
        this.ClientService = ClientService;
    }


    @GetMapping("/getClientList")
    public Map<String, List<Client>> getClientList() {
        return ClientService.getClientList();
    }

    @PostMapping("/editClient")
    public Map<String, String> editClient(@RequestBody Client Client) {
        return ClientService.editClient(Client);
    }

    @PostMapping("/addClient")
    public Map<String, String> addClient(@RequestBody Client Client) {
        return ClientService.addClient(Client);
    }

    @PostMapping("/deleteClient")
    public Map<String, String> deleteClient(@RequestBody Client Client) {
        return ClientService.deleteClient(Client);
    }

    @GetMapping("/page")
    public Map<String, Object> page(@RequestParam Integer page, @RequestParam Integer size, @RequestParam String client_ID, @RequestParam String real_name, @RequestParam String client_phone, @RequestParam String client_address, @RequestParam String client_email) {
        return ClientService.page(page, size, client_ID, real_name, client_phone, client_address, client_email);
    }
}

