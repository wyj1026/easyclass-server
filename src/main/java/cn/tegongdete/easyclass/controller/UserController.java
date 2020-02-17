package cn.tegongdete.easyclass.controller;

import cn.tegongdete.easyclass.mapper.UserMapper;
import cn.tegongdete.easyclass.model.ResponseMessage;
import cn.tegongdete.easyclass.model.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Api(tags = "User Management")
@RestController
@RequestMapping("user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;

    @PostMapping("login")
    public ResponseMessage tryLoigin(User user) {
        try {
            List<User> users = userMapper.selectList(new QueryWrapper<User>()
                    .lambda()
                    .eq(User::getEmail, user.getEmail())
                    .eq(User::getPassword, user.getPassword())
            );
            return users.size() == 0? ResponseMessage.fail(): ResponseMessage.success(users.get(0));
        }
        catch (Exception e) {
            logger.error("Login Error", e);
            return ResponseMessage.fail();
        }
    }


    @PostMapping("/signup")
    public ResponseMessage signUp(User user) {
        try {
            userMapper.insert(user);
        }
        catch (Exception e) {
            logger.error("Signup Error", e);
            return ResponseMessage.fail();
        }
        return ResponseMessage.success(user);
    }

    @PostMapping("/update")
    public ResponseMessage update(User user) {
        try {
            userMapper.updateById(user);
        }
        catch (Exception e) {
            logger.error("Update Error", e);
            return ResponseMessage.fail();
        }
        return ResponseMessage.success();
    }

    @GetMapping("/getById")
    public ResponseMessage getById(int id) {
        try {
            User u = userMapper.selectById(id);
            return ResponseMessage.success(u);
        }
        catch (Exception e) {
            logger.error("GetById Error", e);
            return ResponseMessage.fail();
        }
    }

    @GetMapping("/getBatchById")
    public ResponseMessage getBatchById(Integer[] id) {
        try {
            List<User> users = userMapper.selectBatchIds(Arrays.asList(id));
            return ResponseMessage.success(users);
        }
        catch (Exception e) {
            logger.error("GetBatchById Error", e);
            return ResponseMessage.fail();
        }
    }
}
