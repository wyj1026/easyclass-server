package cn.tegongdete.easyclass.controller;

import cn.tegongdete.easyclass.mapper.UserMapper;
import cn.tegongdete.easyclass.model.ResponseMessage;
import cn.tegongdete.easyclass.model.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "User Management")
@RestController
@RequestMapping("test")
public class Hello {

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    @ApiOperation("login")
    public ResponseMessage tryLoigin(User user) {
        List<User> users = userMapper.selectList(new QueryWrapper<User>()
                .lambda()
                .eq(User::getEmail, user.getEmail())
                .eq(User::getPassword, user.getPassword())
        );
        return users.size() == 0? ResponseMessage.fail(): ResponseMessage.success(users.get(0));
    }
}
