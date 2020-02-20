package cn.tegongdete.easyclass.controller;

import cn.tegongdete.easyclass.mapper.ClassMapper;
import cn.tegongdete.easyclass.mapper.UserClassRoleMapper;
import cn.tegongdete.easyclass.mapper.UserMapper;
import cn.tegongdete.easyclass.model.Class;
import cn.tegongdete.easyclass.model.ResponseMessage;
import cn.tegongdete.easyclass.model.User;
import cn.tegongdete.easyclass.model.UserClassRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import jdk.internal.dynalink.support.ClassMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Class Management")
@RestController
@RequestMapping("class")
public class ClassController {
    private static final Logger logger = LoggerFactory.getLogger(ClassController.class);

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private UserClassRoleMapper userClassRoleMapper;

    @PostMapping("/new")
    public ResponseMessage signUp(Class clas) {
        try {
            classMapper.insert(clas);
        }
        catch (Exception e) {
            logger.error("New Error", e);
            return ResponseMessage.fail();
        }
        return ResponseMessage.success(clas);
    }

    @PostMapping("/update")
    public ResponseMessage update(Class clas) {
        try {
            classMapper.updateById(clas);
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
            Class u = classMapper.selectById(id);
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
            List<Class> classes = classMapper.selectBatchIds(Arrays.asList(id));
            return ResponseMessage.success(classes);
        }
        catch (Exception e) {
            logger.error("GetBatchById Error", e);
            return ResponseMessage.fail();
        }
    }

    @GetMapping("/searchByName")
    public ResponseMessage searchByName(String name) {
        try {
            List<Class> classes = classMapper.selectList(
                    new QueryWrapper<Class>()
                            .lambda()
                            .like(Class::getClassname, name)
            );
            return ResponseMessage.success(classes);
        }
        catch (Exception e) {
            logger.error("GetBatchById Error", e);
            return ResponseMessage.fail();
        }
    }

    @GetMapping("/getBatchByUserId")
    public ResponseMessage getBatchByUserId(Integer id) {
        logger.info(id.toString());
        try {
            List<UserClassRole> userClassRoles = userClassRoleMapper.selectList(new QueryWrapper<UserClassRole>()
                    .lambda()
                    .eq(UserClassRole::getUserId, id)
            );
            List<Integer> classIds = userClassRoles.stream().map(classRole -> {
                return classRole.getClassId();
            }).collect(Collectors.toList());
            if (classIds.isEmpty()) {
                return ResponseMessage.success(Collections.emptyList());
            }
            List<Class> classes = classMapper.selectBatchIds(classIds);
            return ResponseMessage.success(classes);
        }
        catch (Exception e) {
            logger.error("GetBatchByUserId Error", e);
            return ResponseMessage.fail();
        }
    }
}
