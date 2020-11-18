package com.example.mybatis_plus;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis_plus.mapper.UserMapper;
import com.example.mybatis_plus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MybatisPlusApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        /**
         *  参数为条件构造器 null则为查询全部
         */
        List<User> users = userMapper.selectList(null);
        for ( User use: users) {
            System.out.println(use);
        }
    }

    @Test
    void testInsert() {
        User user = new User();
        user.setAge(78);
        user.setEmail("test77@qq.com");
        user.setName("Sean");
        int insert = userMapper.insert(user);
        System.out.println(insert);
        System.out.println(user+"------------");
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setId(9);
        user.setAge(66);
        user.setEmail("test88@qq.com");
        user.setName("alic");
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    @Test
    void  testOptimisticLocker() {
        User user = userMapper.selectById(1L);
        user.setName("big boss");
        user.setAge(55);
        int i = userMapper.updateById(user);
        System.out.println(i+"------------");
    }


    // 测试乐观锁失败！多线程下
    @Test
    void testOptimisticLocker2() {
        // 线程 1
        User user = userMapper.selectById(1L);
        user.setName("kuangshen111");
        user.setEmail("24736743@qq.com");
        // 模拟另外一个线程执行了插队操作
        User user2 = userMapper.selectById(1L);
        user2.setName("kuangshen222");
        user2.setEmail("24736743@qq.com");
        userMapper.updateById(user2);
        // 自旋锁来多次尝试提交！
        userMapper.updateById(user); // 如果没有乐观锁就会覆盖插队线程的值！
    }

    @Test
    void testSelect() {
        //SELECT id,name,age,email,create_time,update_time,version FROM user WHERE id=?
        User user = userMapper.selectById(1L);
        System.out.println(user);

        //SELECT id,name,age,email,create_time,update_time,version FROM user WHERE id IN ( ? , ? , ? )
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        for (User u:users) {
            System.out.println(u);
        }

        //SELECT id,name,age,email,create_time,update_time,version FROM user WHERE name = ? AND age = ?
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("name","Sean");
        stringObjectHashMap.put("age",78);
        List<User> usersMap = userMapper.selectByMap(stringObjectHashMap);
        usersMap.forEach(System.out::println);
    }

    //SELECT id,name,age,email,create_time,update_time,version,deleted FROM user LIMIT 5,5
    @Test
    void testPage() {
        Page<User> userPage = new Page<User>(2,5);
        userMapper.selectPage(userPage,null);

        userPage.getRecords().forEach(System.out::println);
        System.out.println(userPage.getTotal());
    }

    // SELECT id,name,age,email,create_time,update_time,version,deleted FROM user WHERE name IS NOT NULL AND email IS NOT NULL AND age >= ?
    @Test
    void testNoNull() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .isNotNull("name")
                .isNotNull("email")
                .ge("age",12);
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users+"****************************");
        if (users == null){
            System.out.println("-------------");
        }
        users.forEach(System.out::println);
        System.out.println("_________________________");
    }

}
