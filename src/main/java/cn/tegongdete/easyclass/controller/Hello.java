package cn.tegongdete.easyclass.controller;

import cn.tegongdete.easyclass.mapper.UserMapper;
import cn.tegongdete.easyclass.model.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("test")
public class Hello {

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public List<User> testGet() {
//        return new HashMap<String, String>() {{
//            put("name", "springboot");
//        }};
        return userMapper.selectList(null);
    }
}
