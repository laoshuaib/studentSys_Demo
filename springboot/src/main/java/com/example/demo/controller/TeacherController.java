package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.Result;
import com.example.demo.entity.Teacher;
import com.example.demo.mapper.TeacherMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController//返回json数据的controller
@RequestMapping("/teacher")//路由
public class TeacherController {
    @Resource
    TeacherMapper teacherMapper;

    @PostMapping
    //@RequestBody可以直接把传输的json对象映射为主体
    public Result<?> save(@RequestBody Teacher teacher){
        teacherMapper.insert(teacher);
        return Result.success();
    }
    @GetMapping
    public Result<?> find(@RequestParam Integer id) {
        LambdaQueryWrapper<Teacher> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Teacher::getId, id);
        Teacher res =teacherMapper.selectOne(wrapper);
        return Result.success(res);
    }

    @PutMapping
    public Result<?> update(@RequestBody Teacher teacher) {
        teacherMapper.updateById(teacher);
        LambdaQueryWrapper<Teacher> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Teacher::getId, teacher.getId());
        Teacher res =teacherMapper.selectOne(wrapper);
        return Result.success(res);
    }
}
