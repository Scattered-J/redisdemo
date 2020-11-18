package com.example.mybatis_plus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis_plus.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author oliver.hu
 */
@Repository
public interface UserMapper extends BaseMapper<User> {


}
