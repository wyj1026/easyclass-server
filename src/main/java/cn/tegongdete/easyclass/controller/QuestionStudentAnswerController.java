package cn.tegongdete.easyclass.controller;

import cn.tegongdete.easyclass.mapper.HomeworkQuestionAnswerMapper;
import cn.tegongdete.easyclass.mapper.QuestionStudentAnswerMapper;
import cn.tegongdete.easyclass.model.*;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Student Answer Management")
@RestController
@RequestMapping("answer")
public class QuestionStudentAnswerController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionStudentAnswerController.class);

    @Autowired
    private QuestionStudentAnswerMapper mapper;

    @PostMapping("/new")
    public ResponseMessage signUp(QuestionStudentAnswer item) {
        try {
            mapper.insert(item);
        }
        catch (Exception e) {
            logger.error("New Error", e);
            return ResponseMessage.fail();
        }
        return ResponseMessage.success(item);
    }

    @PostMapping("/newbatch")
    public ResponseMessage newbatch(@RequestBody StudentQuestionAnswerBatch batch) {
        try {
            List<QuestionStudentAnswer> answers = Arrays.asList(batch.getAnswers()).stream().map((item)-> {
                QuestionStudentAnswer answer = new QuestionStudentAnswer();
                answer.setClassId(item.getClassId());
                answer.setClassname(item.getClassname());
                answer.setHomeworkId(item.getHomeworkId());
                answer.setUserId(item.getUserId());
                answer.setUsername(item.getUsername());
                answer.setHomeworkQuestionId(item.getHomeworkQuestionId());
                answer.setStudentQuestionAnswer(JSON.toJSONString(item.getStudentQuestionAnswer()));
                answer.setGmtUpload(item.getGmtUpload());
                answer.setGrade(item.getGrade());
                answer.setGmtJudge(item.getGmtJudge());
                answer.setComment(item.getComment());
                return answer;
            }).collect(Collectors.toList());
            for (QuestionStudentAnswer answer: answers) {
                logger.info(answer.getStudentQuestionAnswer());
                mapper.insert(answer);
            }
        }
        catch (Exception e) {
            logger.error("New Error", e);
            return ResponseMessage.fail();
        }
        return ResponseMessage.success();
    }

    @PostMapping("/update")
    public ResponseMessage update(QuestionStudentAnswer item) {
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
            QuestionStudentAnswer u = mapper.selectById(id);
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
