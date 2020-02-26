package cn.tegongdete.easyclass.controller;

import cn.tegongdete.easyclass.mapper.HomeworkQuestionAnswerMapper;
import cn.tegongdete.easyclass.mapper.QuestionStudentAnswerMapper;
import cn.tegongdete.easyclass.mapper.UserMapper;
import cn.tegongdete.easyclass.model.*;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.xdevapi.JsonArray;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private UserMapper userMapper;

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

    @PostMapping("/judgeBatch")
    public ResponseMessage judgeBatch(@RequestBody StudentQuestionAnswerBatch batch) {
        try {
            List<QuestionStudentAnswer> answers = Arrays.asList(batch.getAnswers()).stream().map((item)-> {
                QuestionStudentAnswer answer = new QuestionStudentAnswer();
                answer.setId(item.getId());
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
                mapper.updateById(answer);
            }
        }
        catch (Exception e) {
            logger.error("JudgeBatch Error", e);
            return ResponseMessage.fail();
        }
        return ResponseMessage.success();
    }


    @GetMapping("/getUsersByHomeworkId")
    public ResponseMessage getUsersByHomeworkId(int id, int judged) {
        try {
            List<QuestionStudentAnswer> answers;
            if (judged == 0) {
                answers = mapper.selectList(new QueryWrapper<QuestionStudentAnswer>()
                        .lambda()
                        .eq(QuestionStudentAnswer::getHomeworkId, id).isNotNull(QuestionStudentAnswer::getGrade));
            }
            else {
                answers = mapper.selectList(new QueryWrapper<QuestionStudentAnswer>()
                        .lambda()
                        .eq(QuestionStudentAnswer::getHomeworkId, id).isNull(QuestionStudentAnswer::getGrade));
            }
            logger.info(String.valueOf(answers.size()));
            if (!answers.isEmpty()) {
                List<Integer> userIds = answers.stream().map(answer -> {
                    return answer.getUserId();
                }).collect(Collectors.toList());

                List<User> users = userMapper.selectList(new QueryWrapper<User>()
                        .lambda()
                        .in(User::getId, userIds));
                return ResponseMessage.success(users);
            }
            else {
                return ResponseMessage.success(new ArrayList<>());
            }
        }
        catch (Exception e) {
            logger.error("GetById Error", e);
            return ResponseMessage.fail();
        }
    }


    @GetMapping("/getAnswersByHomeworkIdAndUserId")
    public ResponseMessage getAnswersByHomeworkIdAndUserId(int homeworkId, int userId) {
        try {
            List<QuestionStudentAnswer> answers = mapper.selectList(new QueryWrapper<QuestionStudentAnswer>()
                    .lambda()
                    .eq(QuestionStudentAnswer::getHomeworkId, homeworkId).eq(QuestionStudentAnswer::getUserId, userId));
            List<StudentQuestionAnswerWrap> wraps = answers.stream().map(item -> {
                StudentQuestionAnswerWrap answer = new StudentQuestionAnswerWrap();
                answer.setId(item.getId());
                answer.setClassId(item.getClassId());
                answer.setClassname(item.getClassname());
                answer.setHomeworkId(item.getHomeworkId());
                answer.setUserId(item.getUserId());
                answer.setUsername(item.getUsername());
                answer.setHomeworkQuestionId(item.getHomeworkQuestionId());
                answer.setGmtUpload(item.getGmtUpload());
                answer.setGrade(item.getGrade());
                answer.setGmtJudge(item.getGmtJudge());
                answer.setComment(item.getComment());
                answer.setStudentQuestionAnswer((List) JSON.parse(item.getStudentQuestionAnswer()));
                return answer;
            }).collect(Collectors.toList());
            return ResponseMessage.success(wraps);
        }
        catch (Exception e) {
            logger.error("GetById Error", e);
            return ResponseMessage.fail();
        }
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
