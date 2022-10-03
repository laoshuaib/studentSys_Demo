package com.example.demo.entity;

import lombok.Data;


@Data

public class GradesList {
    private Integer id;
    private Integer classid;
    private String className;
    private Integer studentid;
    private String studentName;
    private Integer teacherid;
    private String scores;
    private int classRank;
    private int lessonRank;
    private String studentNumber;
}
