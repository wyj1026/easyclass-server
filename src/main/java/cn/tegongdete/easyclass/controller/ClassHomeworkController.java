package cn.tegongdete.easyclass.controller;

import cn.tegongdete.easyclass.mapper.ClassHomeworkMapper;
import cn.tegongdete.easyclass.mapper.UserClassRoleMapper;
import cn.tegongdete.easyclass.model.ClassHomework;
import cn.tegongdete.easyclass.model.ResponseMessage;
import cn.tegongdete.easyclass.model.UserClassRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Api(tags = "Homework Management")
@RestController
@RequestMapping("homework")
public class ClassHomeworkController {
    private static final Logger logger = LoggerFactory.getLogger(ClassHomeworkController.class);

    @Autowired
    private ClassHomeworkMapper mapper;

    @PostMapping("/new")
    public ResponseMessage signUp(ClassHomework item) {
        try {
            mapper.insert(item);
        }
        catch (Exception e) {
            logger.error("New Error", e);
            return ResponseMessage.fail();
        }
        return ResponseMessage.success(item);
    }

    @PostMapping("/update")
    public ResponseMessage update(ClassHomework item) {
        try {
            mapper.updateById(item);
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
            ClassHomework u = mapper.selectById(id);
            return ResponseMessage.success(u);
        }
        catch (Exception e) {
            logger.error("GetById Error", e);
            return ResponseMessage.fail();
        }
    }

    @GetMapping("/deleteById")
    public ResponseMessage deleteById(int id) {
        try {
            mapper.deleteById(id);
            return ResponseMessage.success();
        }
        catch (Exception e) {
            logger.error("DeleteById Error", e);
            return ResponseMessage.fail();
        }
    }
}
