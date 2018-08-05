package com.haron.pro.dao.mapper;

import com.haron.pro.dao.entity.DateRemind;
import com.haron.pro.dao.entity.DateRemindExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DateRemindMapper {

    int countByExample(DateRemindExample example);


    int deleteByExample(DateRemindExample example);


    int deleteByPrimaryKey(Integer id);


    int insert(DateRemind record);


    int insertSelective(DateRemind record);


    List<DateRemind> selectByExample(DateRemindExample example);


    DateRemind selectByPrimaryKey(Integer id);


    int updateByExampleSelective(@Param("record") DateRemind record, @Param("example") DateRemindExample example);


    int updateByExample(@Param("record") DateRemind record, @Param("example") DateRemindExample example);


    int updateByPrimaryKeySelective(DateRemind record);


    int updateByPrimaryKey(DateRemind record);
    
    List<DateRemind> selectToRemind(String nextRemindDate);

    Integer updateRemindByIds(List<Integer> list);

}