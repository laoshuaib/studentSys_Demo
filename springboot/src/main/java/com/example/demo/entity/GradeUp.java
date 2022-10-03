package com.example.demo.entity;

import lombok.Data;

@Data
public class GradeUp {

    private Integer id;
    private Integer classid;
    private String className;
    private Integer teacherid;
    private Integer all;//学生总数
    private Integer checkNumber;//批改总数
}
