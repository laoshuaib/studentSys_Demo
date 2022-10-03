package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.LessonList;
import com.example.demo.mapper.LessonListMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController//返回json数据的controller
@RequestMapping("/lessonlist")//路由

public class LessonListController {
    @Resource
    LessonListMapper lessonListMapper;

    @PostMapping
    //@RequestBody可以直接把传输的json对象映射为主体
    public Result<?> save(@RequestBody LessonList lessonList){
        if(lessonList.getStartTime().after(lessonList.getEndTime()))
            return Result.error("-1", "选课截止时间早于开始时间");
        Date endDate = new Date();
        endDate.setTime(lessonList.getEndTime().getTime()+1000*60*60*24*2);
        lessonList.setEndTime(endDate);
        Date startDate = new Date();
        startDate.setTime(lessonList.getStartTime().getTime()+1000*60*60*24*2);
        lessonList.setStartTime(startDate);
        lessonListMapper.insert(lessonList);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<LessonList> wrapper = Wrappers.lambdaQuery();
        Date date =new Date();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(LessonList::getName, search).ge(LessonList::getEndTime,date).le(LessonList::getStartTime,date);
        }
        else
            wrapper.ge(LessonList::getEndTime,date).le(LessonList::getStartTime,date);
        Page<LessonList> BookPage =lessonListMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(BookPage);
    }
    @GetMapping("/manage")
    public Result<?> findManagePage(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam Integer id) {
        LambdaQueryWrapper<LessonList> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LessonList::getTeacherid,id);
        Page<LessonList> BookPage =lessonListMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(BookPage);
    }
    @DeleteMapping("/{lessonID}")
    public Result<?> update(@PathVariable Long lessonID) {
        LambdaQueryWrapper<LessonList> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LessonList::getLessonID,lessonID);
        lessonListMapper.delete(wrapper);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody LessonList lessonList) {
        lessonListMapper.updateById(lessonList);
        return Result.success();
    }
}
