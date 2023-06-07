package com.example.backend.dto;

import lombok.Data;

@Data
public class EditMember {
    private String id;
    private String depart_no;
    private String depart_no_new;
    private String dep_depart_no;
    private String dep_depart_no_new;
    private String bank_name;
    private String bank_name_new;
    private String name_new;
    private String phone_new;
    private String address_new;
    private String salary;
    private String salary_new;
    private String level;
    private String level_new;
}
