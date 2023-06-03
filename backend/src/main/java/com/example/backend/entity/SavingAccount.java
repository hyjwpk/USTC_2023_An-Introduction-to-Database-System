package com.example.backend.entity;

import lombok.Data;

@Data
public class SavingAccount {
    private Integer account_id;
    private String client_id;
    private String bank_name;
    private String password;
    private Integer remaining;
    private String open_date;
    private Float interest_rate;
}
