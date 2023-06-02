package com.example.backend.entity;

import lombok.Data;

@Data
public class Loan {
    private Integer loan_id;
    private String client_id;
    private String bank_name;
    private Integer loan_total;
    private Integer remain_loan;
    private String loan_date;
    private Float loan_rate;
}