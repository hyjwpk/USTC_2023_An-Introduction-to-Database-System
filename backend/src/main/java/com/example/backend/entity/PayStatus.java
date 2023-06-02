package com.example.backend.entity;

import lombok.Data;

@Data
public class PayStatus {
    private Integer pay_id;
    private Integer loan_id;
    private Integer pay_money;
    private String pay_date;
}
