package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {
    @TableId(value = "id", type = IdType.AUTO)//自动生成
    private Integer id;
    private String username;
    private String password;
    private String number;
    private String identity;
}
