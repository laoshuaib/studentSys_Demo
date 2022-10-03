package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("student")
@Data
public class Student {
    private Integer id;
    private String name;
    private String major;
    private String number;//学号
    private String grade;
    private String birthday;
    private String phoneNumber;
    private String email;
    private String address;
}
