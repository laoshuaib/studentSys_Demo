package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.*;
import com.example.demo.mapper.ChooseListMapper;
import com.example.demo.mapper.LessonListMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController//返回json数据的controller
@RequestMapping("/chooselist")//路由

public class ChooseListController {
    @Resource
    ChooseListMapper chooseListMapper;
    @Resource
    LessonListMapper lessonListMapper;

    @PostMapping
    //@RequestBody可以直接把传输的json对象映射为主体
    public Result<?> save(@RequestBody ChooseList chooseList){
        ChooseList res = chooseListMapper.selectOne(Wrappers.<ChooseList>lambdaQuery().eq(ChooseList::getClassid, chooseList.getClassid()).eq(ChooseList::getStudentid,chooseList.getStudentid()));
        if (res != null) {
            return Result.error("-1", "已选过该课程");
        }
        chooseListMapper.insert(chooseList);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam Integer id) {
        LambdaQueryWrapper<ChooseList> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ChooseList::getStudentid, id);
        Page<ChooseList> BookPage =chooseListMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<ChooseList> chooseLists=BookPage.getRecords();
        ChooseList[] result=chooseLists.toArray(new ChooseList[chooseLists.size()]);
        Date date = new Date();
        ArrayList<ChooseList> chooseListArrayList = new ArrayList<>();
        for(int i=0;i<chooseLists.size();i++){
            LambdaQueryWrapper<LessonList> lessonListLambdaQueryWrapper=Wrappers.lambdaQuery();
            lessonListLambdaQueryWrapper.eq(LessonList::getLessonID,result[i].getClassid());
            LessonList lessonList = lessonListMapper.selectOne(lessonListLambdaQueryWrapper);
            if(lessonList.getStartTime().before(date)&&lessonList.getEndTime().after(date))
                chooseListArrayList.add(result[i]);
        }
        Page<ChooseList> chooseListPage=new Page<>(pageNum, pageSize);
        chooseListPage.setRecords(chooseListArrayList);
        chooseListPage.setTotal(chooseListArrayList.size());
        return Result.success(chooseListPage);
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        chooseListMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping("/getgrades")
    public Result<?> findRank(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam Integer id) {
        LambdaQueryWrapper<ChooseList> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ChooseList::getStudentid, id).ne(ChooseList::getScores,-1).ne(ChooseList::getChecker,0);//筛去未录入部分，筛去未锁定部分
        Page<ChooseList> BookPage =chooseListMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<ChooseList> chooseLists=BookPage.getRecords();
        ChooseList[] result=chooseLists.toArray(new ChooseList[chooseLists.size()]);
        GradesList[] gradesLists=new GradesList[chooseLists.size()];
        for(int i=0;i<chooseLists.size();i++){
            GradesList gradesList=new GradesList();
            gradesLists[i]=gradesList;
            gradesLists[i].setClassid(result[i].getClassid());
            gradesLists[i].setId(result[i].getId());
            gradesLists[i].setScores(result[i].getScores());
            gradesLists[i].setStudentid(result[i].getStudentid());
            gradesLists[i].setClassName(result[i].getClassName());
            gradesLists[i].setStudentNumber(result[i].getStudentNumber());
            gradesLists[i].setTeacherid(result[i].getTeacherid());
            gradesLists[i].setClassRank(rankByClass(result[i].getClassid(),result[i].getScores()));
            gradesLists[i].setLessonRank(rankByLesson(result[i].getClassName(),result[i].getScores()));
        }
        List<GradesList> gradesListss= Arrays.asList(gradesLists);
        Page<GradesList> gradesListPage=new Page<>(pageNum, pageSize);
        gradesListPage.setRecords(gradesListss);
        gradesListPage.setTotal(chooseLists.size());
        return Result.success(gradesListPage);
    }

    public int rankByClass(int classid,String idGrades){
        int rank=1;
        int grade=Integer.parseInt(idGrades);
        LambdaQueryWrapper<ChooseList> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ChooseList::getClassid,classid);
        List<ChooseList> chooseLists=chooseListMapper.selectList(wrapper);
        ChooseList[] result=chooseLists.toArray(new ChooseList[chooseLists.size()]);
        for(int i=0;i<chooseLists.size();i++){
            int score=Integer.parseInt(result[i].getScores());
            if(score>grade)
                rank++;
        }
        return rank;
    }

    public int rankByLesson(String className,String idGrades){
        int rank=1;
        int grade=Integer.parseInt(idGrades);
        LambdaQueryWrapper<ChooseList> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ChooseList::getClassName,className);
        List<ChooseList> chooseLists=chooseListMapper.selectList(wrapper);
        ChooseList[] result=chooseLists.toArray(new ChooseList[chooseLists.size()]);
        for(int i=0;i<chooseLists.size();i++){
            int score=Integer.parseInt(result[i].getScores());
            if(score>grade)
                rank++;
        }
        return rank;
    }

    @GetMapping("/input")
    public Result<?> Input(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam Integer id) {
        LambdaQueryWrapper<ChooseList> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ChooseList::getTeacherid, id).ne(ChooseList::getChecker,1);//筛去锁定部分
        Page<ChooseList> BookPage =chooseListMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<ChooseList> chooseLists=BookPage.getRecords();
        ChooseList[] result=chooseLists.toArray(new ChooseList[chooseLists.size()]);
        GradesList[] gradesLists=new GradesList[chooseLists.size()];
        for(int i=0;i<chooseLists.size();i++){
            GradesList gradesList=new GradesList();
            gradesLists[i]=gradesList;
            gradesLists[i].setClassid(result[i].getClassid());
            gradesLists[i].setId(result[i].getId());
            gradesLists[i].setScores(result[i].getScores());
            gradesLists[i].setStudentid(result[i].getStudentid());
            gradesLists[i].setStudentName(result[i].getStudentName());
            gradesLists[i].setClassName(result[i].getClassName());
            gradesLists[i].setStudentNumber(result[i].getStudentNumber());
            gradesLists[i].setClassRank(rankByClass(result[i].getClassid(),result[i].getScores()));
            gradesLists[i].setLessonRank(rankByLesson(result[i].getClassName(),result[i].getScores()));
        }
        List<GradesList> gradesListss= Arrays.asList(gradesLists);
        Page<GradesList> gradesListPage=new Page<>(pageNum, pageSize);
        gradesListPage.setRecords(gradesListss);
        gradesListPage.setTotal(chooseLists.size());
        return Result.success(gradesListPage);
    }

    @GetMapping("/inputByID")
    public Result<?> InputByID(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam Integer id,
                               @RequestParam String search) {
        LambdaQueryWrapper<ChooseList> wrapper = Wrappers.lambdaQuery();
        int classID=Integer.parseInt(search);
        wrapper.eq(ChooseList::getTeacherid, id).ne(ChooseList::getChecker,1).eq(ChooseList::getClassid,classID);//筛去锁定部分
        Page<ChooseList> BookPage =chooseListMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<ChooseList> chooseLists=BookPage.getRecords();
        ChooseList[] result=chooseLists.toArray(new ChooseList[chooseLists.size()]);
        GradesList[] gradesLists=new GradesList[chooseLists.size()];
        for(int i=0;i<chooseLists.size();i++){
            GradesList gradesList=new GradesList();
            gradesLists[i]=gradesList;
            gradesLists[i].setClassid(result[i].getClassid());
            gradesLists[i].setId(result[i].getId());
            gradesLists[i].setScores(result[i].getScores());
            gradesLists[i].setStudentid(result[i].getStudentid());
            gradesLists[i].setStudentName(result[i].getStudentName());
            gradesLists[i].setClassName(result[i].getClassName());
            gradesLists[i].setStudentNumber(result[i].getStudentNumber());
            gradesLists[i].setClassRank(rankByClass(result[i].getClassid(),result[i].getScores()));
            gradesLists[i].setLessonRank(rankByLesson(result[i].getClassName(),result[i].getScores()));
        }
        List<GradesList> gradesListss= Arrays.asList(gradesLists);
        Page<GradesList> gradesListPage=new Page<>(pageNum, pageSize);
        gradesListPage.setRecords(gradesListss);
        gradesListPage.setTotal(chooseLists.size());
        return Result.success(gradesListPage);
    }

    @GetMapping("/studentlist")
    public Result<?> studentList(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam Integer id) {
        LambdaQueryWrapper<ChooseList> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ChooseList::getTeacherid, id);
        Page<ChooseList> BookPage =chooseListMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<ChooseList> chooseLists=BookPage.getRecords();
        ChooseList[] result=chooseLists.toArray(new ChooseList[chooseLists.size()]);
        GradesList[] gradesLists=new GradesList[chooseLists.size()];
        for(int i=0;i<chooseLists.size();i++){
            GradesList gradesList=new GradesList();
            gradesLists[i]=gradesList;
            gradesLists[i].setClassid(result[i].getClassid());
            gradesLists[i].setId(result[i].getId());
            gradesLists[i].setScores(result[i].getScores());
            gradesLists[i].setStudentid(result[i].getStudentid());
            gradesLists[i].setStudentName(result[i].getStudentName());
            gradesLists[i].setClassName(result[i].getClassName());
            gradesLists[i].setStudentNumber(result[i].getStudentNumber());
            gradesLists[i].setClassRank(rankByClass(result[i].getClassid(),result[i].getScores()));
            gradesLists[i].setLessonRank(rankByLesson(result[i].getClassName(),result[i].getScores()));
        }
        List<GradesList> gradesListss= Arrays.asList(gradesLists);
        Page<GradesList> gradesListPage=new Page<>(pageNum, pageSize);
        gradesListPage.setRecords(gradesListss);
        gradesListPage.setTotal(chooseLists.size());
        return Result.success(gradesListPage);
    }

    @GetMapping("/studentListByID")
    public Result<?> studentListByID(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam Integer id,
                               @RequestParam String search) {
        LambdaQueryWrapper<ChooseList> wrapper = Wrappers.lambdaQuery();
        int classID=Integer.parseInt(search);
        wrapper.eq(ChooseList::getTeacherid, id).eq(ChooseList::getClassid,classID);//筛去锁定部分
        Page<ChooseList> BookPage =chooseListMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<ChooseList> chooseLists=BookPage.getRecords();
        ChooseList[] result=chooseLists.toArray(new ChooseList[chooseLists.size()]);
        GradesList[] gradesLists=new GradesList[chooseLists.size()];
        for(int i=0;i<chooseLists.size();i++){
            GradesList gradesList=new GradesList();
            gradesLists[i]=gradesList;
            gradesLists[i].setClassid(result[i].getClassid());
            gradesLists[i].setId(result[i].getId());
            gradesLists[i].setScores(result[i].getScores());
            gradesLists[i].setStudentid(result[i].getStudentid());
            gradesLists[i].setStudentName(result[i].getStudentName());
            gradesLists[i].setClassName(result[i].getClassName());
            gradesLists[i].setStudentNumber(result[i].getStudentNumber());
            gradesLists[i].setClassRank(rankByClass(result[i].getClassid(),result[i].getScores()));
            gradesLists[i].setLessonRank(rankByLesson(result[i].getClassName(),result[i].getScores()));
        }
        List<GradesList> gradesListss= Arrays.asList(gradesLists);
        Page<GradesList> gradesListPage=new Page<>(pageNum, pageSize);
        gradesListPage.setRecords(gradesListss);
        gradesListPage.setTotal(chooseLists.size());
        return Result.success(gradesListPage);
    }
    @GetMapping("/view")
    public Result<?> view(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam Integer id) {
        LambdaQueryWrapper<ChooseList> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ChooseList::getTeacherid, id).ne(ChooseList::getChecker,0);//筛去未锁定部分
        Page<ChooseList> BookPage =chooseListMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<ChooseList> chooseLists=BookPage.getRecords();
        ChooseList[] result=chooseLists.toArray(new ChooseList[chooseLists.size()]);
        GradesList[] gradesLists=new GradesList[chooseLists.size()];
        for(int i=0;i<chooseLists.size();i++){
            GradesList gradesList=new GradesList();
            gradesLists[i]=gradesList;
            gradesLists[i].setClassid(result[i].getClassid());
            gradesLists[i].setId(result[i].getId());
            gradesLists[i].setScores(result[i].getScores());
            gradesLists[i].setStudentid(result[i].getStudentid());
            gradesLists[i].setStudentName(result[i].getStudentName());
            gradesLists[i].setClassName(result[i].getClassName());
            gradesLists[i].setStudentNumber(result[i].getStudentNumber());
            gradesLists[i].setClassRank(rankByClass(result[i].getClassid(),result[i].getScores()));
            gradesLists[i].setLessonRank(rankByLesson(result[i].getClassName(),result[i].getScores()));
        }
        List<GradesList> gradesListss= Arrays.asList(gradesLists);
        Page<GradesList> gradesListPage=new Page<>(pageNum, pageSize);
        gradesListPage.setRecords(gradesListss);
        gradesListPage.setTotal(chooseLists.size());
        return Result.success(gradesListPage);
    }

    @GetMapping("/viewByID")
    public Result<?> viewByID(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam Integer id,
                               @RequestParam String search) {
        LambdaQueryWrapper<ChooseList> wrapper = Wrappers.lambdaQuery();
        int classID=Integer.parseInt(search);
        wrapper.eq(ChooseList::getTeacherid, id).ne(ChooseList::getChecker,0).eq(ChooseList::getClassid,classID);//筛去未锁定部分
        Page<ChooseList> BookPage =chooseListMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<ChooseList> chooseLists=BookPage.getRecords();
        ChooseList[] result=chooseLists.toArray(new ChooseList[chooseLists.size()]);
        GradesList[] gradesLists=new GradesList[chooseLists.size()];
        for(int i=0;i<chooseLists.size();i++){
            GradesList gradesList=new GradesList();
            gradesLists[i]=gradesList;
            gradesLists[i].setClassid(result[i].getClassid());
            gradesLists[i].setId(result[i].getId());
            gradesLists[i].setScores(result[i].getScores());
            gradesLists[i].setStudentid(result[i].getStudentid());
            gradesLists[i].setStudentName(result[i].getStudentName());
            gradesLists[i].setClassName(result[i].getClassName());
            gradesLists[i].setStudentNumber(result[i].getStudentNumber());
            gradesLists[i].setClassRank(rankByClass(result[i].getClassid(),result[i].getScores()));
            gradesLists[i].setLessonRank(rankByLesson(result[i].getClassName(),result[i].getScores()));
        }
        List<GradesList> gradesListss= Arrays.asList(gradesLists);
        Page<GradesList> gradesListPage=new Page<>(pageNum, pageSize);
        gradesListPage.setRecords(gradesListss);
        gradesListPage.setTotal(chooseLists.size());
        return Result.success(gradesListPage);
    }

    @GetMapping("/schedule")
    public Result<?> Schedule(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam Integer id) {
        LambdaQueryWrapper<ChooseList> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ChooseList::getStudentid, id);
        Page<ChooseList> BookPage =chooseListMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<ChooseList> chooseLists=BookPage.getRecords();
        ChooseList[] result=chooseLists.toArray(new ChooseList[chooseLists.size()]);
        Schedule[] schedules=new Schedule[5];
        for(int i=0;i<5;i++){
            Schedule schedule=new Schedule();
            schedules[i]=schedule;
        }
        schedules[0].setTime("第一节");
        schedules[1].setTime("第二节");
        schedules[2].setTime("第三节");
        schedules[3].setTime("第四节");
        schedules[4].setTime("第五节");
        for(int i=0;i<chooseLists.size();i++){
            LambdaQueryWrapper<LessonList> lessonListLambdaQueryWrapper = Wrappers.lambdaQuery();
            lessonListLambdaQueryWrapper.eq(LessonList::getLessonID,result[i].getClassid());
            LessonList lessonList;
            lessonList=lessonListMapper.selectOne(lessonListLambdaQueryWrapper);
            if(lessonList==null)
                continue;
            //解析时间
            String time = lessonList.getTime();
            String week = time.substring(0,2);//周几
            String day = time.substring(2,5);//第几节
            int dayIndex=dayTransform(day);
            switch (week){
                case "周一":{
                    schedules[dayIndex-1].setOne(lessonList.getName());
                    break;
                }
                case "周二":{
                    schedules[dayIndex-1].setTwo(lessonList.getName());
                    break;
                }
                case "周三":{
                    schedules[dayIndex-1].setThree(lessonList.getName());
                    break;
                }
                case "周四":{
                    schedules[dayIndex-1].setFour(lessonList.getName());
                    break;
                }
                case "周五":{
                    schedules[dayIndex-1].setFive(lessonList.getName());
                    break;
                }
                case "周六":{
                    schedules[dayIndex-1].setSix(lessonList.getName());
                    break;
                }
                case "周日":{
                    schedules[dayIndex-1].setSeven(lessonList.getName());
                    break;
                }
            }
        }
        List<Schedule> scheduleList= Arrays.asList(schedules);
        Page<Schedule> schedulePage=new Page<>(pageNum, pageSize);
        schedulePage.setRecords(scheduleList);
        return Result.success(schedulePage);
    }
    int dayTransform(String day){
       int result=0;
       switch (day.charAt(1)){
           case '一': result=1;break;
           case '二': result=2;break;
           case '三': result=3;break;
           case '四': result=4;break;
           case '五': result=5;break;
       }
       return result;
    }

    @GetMapping("/gradeup")
    public Result<?> gradeup(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam Integer id) {
        LambdaQueryWrapper<LessonList> lessonListLambdaQueryWrapper = Wrappers.lambdaQuery();
        lessonListLambdaQueryWrapper.eq(LessonList::getTeacherid,id);
        Page<LessonList> BookPage =lessonListMapper.selectPage(new Page<>(pageNum, pageSize), lessonListLambdaQueryWrapper);
        List<LessonList> lessonLists=BookPage.getRecords();
        LessonList[] result=lessonLists.toArray(new LessonList[lessonLists.size()]);
        GradeUp[] gradeUps=new GradeUp[lessonLists.size()];
        LambdaQueryWrapper<ChooseList> chooseListLambdaQueryWrapper=Wrappers.lambdaQuery();
        for(int i=0;i<lessonLists.size();i++){
            GradeUp gradeUp=new GradeUp();
            gradeUps[i]=gradeUp;
            gradeUps[i].setClassid(result[i].getLessonID());
            gradeUps[i].setClassName(result[i].getName());
            chooseListLambdaQueryWrapper.eq(ChooseList::getClassid,result[i].getLessonID()).ne(ChooseList::getChecker,1);
            Page<ChooseList> chooseListPage=chooseListMapper.selectPage(new Page<>(pageNum, pageSize), chooseListLambdaQueryWrapper);
            List<ChooseList> chooseLists=chooseListPage.getRecords();
            ChooseList[] temp=chooseLists.toArray(new ChooseList[chooseLists.size()]);
            gradeUps[i].setAll(temp.length);
            int checkNumber=0;
            for(int j=0;j<temp.length;j++){
                if(Integer.parseInt(temp[j].getScores())!=-1)
                    checkNumber++;
            }
            gradeUps[i].setCheckNumber(checkNumber);
        }

        List<GradeUp> gradesLists= Arrays.asList(gradeUps);
        Page<GradeUp> gradeUpPage=new Page<>(pageNum, pageSize);
        gradeUpPage.setRecords(gradesLists);
        gradeUpPage.setTotal(gradesLists.size());
        return Result.success(gradeUpPage);
    }
    @PutMapping
    public Result<?> update(@RequestBody ChooseList chooseList) {
        chooseListMapper.updateById(chooseList);
        return Result.success();
    }

    @PutMapping("/check")
    public Result<?> updateChecker(@RequestBody GradeUp gradeUp) {
        chooseListMapper.updateChecker(1,gradeUp.getClassid());
        return Result.success();
    }
}

