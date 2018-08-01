package com.haron.pro.dao.mapper;

import com.haron.pro.dao.entity.ChatLog;
import com.haron.pro.dao.entity.ChatLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChatLogMapper {

    int countByExample(ChatLogExample example);


    int deleteByExample(ChatLogExample example);


    int deleteByPrimaryKey(Integer id);


    int insert(ChatLog record);


    int insertSelective(ChatLog record);


    List<ChatLog> selectByExample(ChatLogExample example);


    ChatLog selectByPrimaryKey(Integer id);


    int updateByExampleSelective(@Param("record") ChatLog record, @Param("example") ChatLogExample example);


    int updateByExample(@Param("record") ChatLog record, @Param("example") ChatLogExample example);


    int updateByPrimaryKeySelective(ChatLog record);


    int updateByPrimaryKey(ChatLog record);
}