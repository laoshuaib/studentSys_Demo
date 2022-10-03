package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("chooselist")
@Data
public class ChooseList {
    @TableId(value = "id", type = IdType.AUTO)//自动生成
    private Integer id;
    private Integer classid;
    private String className;
    private Integer studentid;
    private String studentName;
    private String scores;
    private Integer checker;
    private String studentNumber;
    private Integer teacherid;
}
