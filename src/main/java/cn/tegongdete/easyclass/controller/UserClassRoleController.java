package cn.tegongdete.easyclass.controller;

import cn.tegongdete.easyclass.mapper.ClassMapper;
import cn.tegongdete.easyclass.mapper.UserClassRoleMapper;
import cn.tegongdete.easyclass.model.Class;
import cn.tegongdete.easyclass.model.ResponseMessage;
import cn.tegongdete.easyclass.model.User;
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

@Api(tags = "Role Management")
@RestController
@RequestMapping("role")
public class UserClassRoleController {
    private static final Logger logger = LoggerFactory.getLogger(UserClassRoleController.class);

    @Autowired
    private UserClassRoleMapper mapper;

    @PostMapping("/new")
    public ResponseMessage signUp(UserClassRole role) {
        try {
            mapper.insert(role);
        }
        catch (Exception e) {
            logger.error("New Error", e);
            return ResponseMessage.fail();
        }
        return ResponseMessage.success(role);
    }

    @PostMapping("/update")
    public ResponseMessage update(UserClassRole role) {
        try {
            mapper.updateById(role);
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
            UserClassRole u = mapper.selectById(id);
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
            List<UserClassRole> classes = mapper.selectBatchIds(Arrays.asList(id));
            return ResponseMessage.success(classes);
        }
        catch (Exception e) {
            logger.error("GetBatchById Error", e);
            return ResponseMessage.fail();
        }
    }

    @GetMapping("/getBatchByIdAndRole")
    public ResponseMessage getBatchByClassAndRole(Integer id, String role) {
        try {
            List<UserClassRole> classes = mapper.selectList(
                    new QueryWrapper<UserClassRole>()
                            .lambda()
                            .eq(UserClassRole::getRole, role)
                    .eq(UserClassRole::getId, id)
            );
            return ResponseMessage.success(classes);
        }
        catch (Exception e) {
            logger.error("GetBatchByIdAndRole Error", e);
            return ResponseMessage.fail();
        }
    }
}
