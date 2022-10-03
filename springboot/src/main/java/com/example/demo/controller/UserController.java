package com.example.demo.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.example.demo.common.Result;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;
import com.example.demo.entity.User;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController//返回json数据的controller
@RequestMapping("/user")//路由

public class UserController {
    @Resource
    UserMapper userMapper;
    @Resource
    TeacherMapper teacherMapper;
    @Resource
    StudentMapper studentMapper;

    @PostMapping
    //@RequestBody可以直接把传输的json对象映射为主体
    public Result<?> save(@RequestBody User user) {
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }

    @PostMapping("/login")
    //@RequestBody可以直接把传输的json对象映射为主体
    public Result<?> login(@RequestBody User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getNumber, user.getNumber()).eq(User::getPassword, user.getPassword()));
        if (res == null) {
            return Result.error("-1", "学号工号或密码错误");
        }
        return Result.success(res);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getNumber, user.getNumber()));
        if (res != null) {
            return Result.error("-1", "学号或工号重复");
        }
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }//默认密码，为弥补操作
        userMapper.insert(user);
        res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getNumber, user.getNumber()));
        //写入学生或老师表
        if(user.getIdentity().equals("老师")){
            Teacher teacher = new Teacher();
            teacher.setName(res.getUsername());
            teacher.setNumber(res.getNumber());
            teacher.setId(res.getId());
            teacherMapper.insert(teacher);
        }
        if(user.getIdentity().equals("学生")){
            Student student =new Student();
            student.setName(user.getUsername());
            student.setNumber(user.getNumber());
            student.setId(user.getId());
            studentMapper.insert(student);
        }
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody User user) {
        userMapper.updateById(user);
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getNumber, user.getNumber()).eq(User::getPassword, user.getPassword()));
        return Result.success(res);
    }
}
