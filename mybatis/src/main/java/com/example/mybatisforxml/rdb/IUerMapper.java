package com.example.mybatisforxml.rdb;

import com.example.mybatisforxml.dao.User;
import jdk.internal.instrumentation.TypeMapping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author oliver.hu
 */
@Mapper
public interface IUerMapper {

    /**
     * 查询所有
     * @return user封装好的实体类
     */
    @Select("select * for user")
    List<User> findAll();
}
