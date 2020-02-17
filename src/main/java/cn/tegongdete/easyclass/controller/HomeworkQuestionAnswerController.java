package cn.tegongdete.easyclass.controller;

import cn.tegongdete.easyclass.mapper.HomeworkQuestionAnswerMapper;
import cn.tegongdete.easyclass.model.HomeworkQuestionAnswer;
import cn.tegongdete.easyclass.model.ResponseMessage;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Homework Question Management")
@RestController
@RequestMapping("question")
public class HomeworkQuestionAnswerController {
    private static final Logger logger = LoggerFactory.getLogger(HomeworkQuestionAnswerController.class);

    @Autowired
    private HomeworkQuestionAnswerMapper mapper;

    @PostMapping("/new")
    public ResponseMessage signUp(HomeworkQuestionAnswer item) {
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
    public ResponseMessage update(HomeworkQuestionAnswer item) {
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
            HomeworkQuestionAnswer u = mapper.selectById(id);
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
