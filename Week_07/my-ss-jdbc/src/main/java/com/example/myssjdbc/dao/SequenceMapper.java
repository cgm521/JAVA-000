package com.example.myssjdbc.dao;

import com.example.myssjdbc.entity.Sequence;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SequenceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Sequence record);

    Sequence selectByPrimaryKey(Long id);

    Sequence selectByName(String name);

    List<Sequence> selectAll();

    int updateByPrimaryKey(Sequence record);
}