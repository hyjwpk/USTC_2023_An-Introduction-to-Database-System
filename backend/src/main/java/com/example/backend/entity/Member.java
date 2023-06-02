package com.example.backend.entity;

import lombok.Data;

@Data
public class Member {
    Integer id;
    Integer depart_no;
    Integer dep_depart_no;
    String bank_name;
    String name;
    String sex;
    String person_id;
    String phone;
    String address;
    Integer salary;
    String begin_date;
    Integer level;
}
