package cn.tegongdete.easyclass.controller;

import cn.tegongdete.easyclass.mapper.ClassMapper;
import cn.tegongdete.easyclass.mapper.UserMapper;
import cn.tegongdete.easyclass.model.Class;
import cn.tegongdete.easyclass.model.ResponseMessage;
import cn.tegongdete.easyclass.model.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.List;

@Api(tags = "Class Management")
@RestController
@RequestMapping("class")
public class ClassController {
    private static final Logger logger = LoggerFactory.getLogger(ClassController.class);

    @Autowired
    private ClassMapper classMapper;

    @PostMapping("/newClass")
    public ResponseMessage signUp(Class clas) {
        try {
            classMapper.insert(clas);
        }
        catch (Exception e) {
            logger.error("NewClass Error", e);
            return ResponseMessage.fail();
        }
        return ResponseMessage.success(clas);
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
}
