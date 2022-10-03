package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.Result;
import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.untils.CheckUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController//返回json数据的controller
@RequestMapping("/student")//路由
public class StudentController {
    @Resource
    StudentMapper studentMapper;

    @PostMapping
    //@RequestBody可以直接把传输的json对象映射为主体
    public Result<?> save(@RequestBody Student student){
        studentMapper.insert(student);
        return Result.success();
    }
    @GetMapping
    public Result<?> find(@RequestParam Integer id) {
        LambdaQueryWrapper<Student> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Student::getId, id);
        Student res =studentMapper.selectOne(wrapper);
        return Result.success(res);
    }

    @PutMapping
    public Result<?> update(@RequestBody Student student) {
        if(student.getPhoneNumber()==null){
           if(student.getEmail()==null){
               studentMapper.updateById(student);
               LambdaQueryWrapper<Student> wrapper = Wrappers.lambdaQuery();
               wrapper.eq(Student::getId, student.getId());
               Student res = studentMapper.selectOne(wrapper);
               return Result.success(res);
           }
           if(CheckUtil.isEmail(student.getEmail())){
               studentMapper.updateById(student);
               LambdaQueryWrapper<Student> wrapper = Wrappers.lambdaQuery();
               wrapper.eq(Student::getId, student.getId());
               Student res = studentMapper.selectOne(wrapper);
               return Result.success(res);
           }
           return Result.error("-1","请检查邮箱格式");
        }
        if(CheckUtil.isPhone(student.getPhoneNumber())){
            if(student.getEmail()==null){
                studentMapper.updateById(student);
                LambdaQueryWrapper<Student> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(Student::getId, student.getId());
                Student res = studentMapper.selectOne(wrapper);
                return Result.success(res);
            }
            if(CheckUtil.isEmail(student.getEmail())){
                studentMapper.updateById(student);
                LambdaQueryWrapper<Student> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(Student::getId, student.getId());
                Student res = studentMapper.selectOne(wrapper);
                return Result.success(res);
            }
            return Result.error("-1","请检查邮箱格式");
        }
        return Result.error("-1","请检查手机号是否正确");



    }
}
