package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.ChooseList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


public interface ChooseListMapper extends BaseMapper<ChooseList> {

    @Update("UPDATE chooselist SET checker = #{checker} WHERE classid = #{classid}")
    void updateChecker(@Param("checker") int checker, @Param("classid") int classid);

}
