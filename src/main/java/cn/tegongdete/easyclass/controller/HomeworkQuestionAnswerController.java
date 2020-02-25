package cn.tegongdete.easyclass.controller;

import cn.tegongdete.easyclass.mapper.HomeworkQuestionAnswerMapper;
import cn.tegongdete.easyclass.model.*;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @PostMapping("/newbatch")
    public ResponseMessage newbatch(@RequestBody HomeworkQuestionAnswerBatch batch) {
        try {
            List<HomeworkQuestionAnswer> answers = Arrays.asList(batch.getQuestions()).stream().map((item)-> {
                HomeworkQuestionAnswer answer = new HomeworkQuestionAnswer();
                answer.setGmtCreate(item.getGmtCreate());
                answer.setClassId(item.getClassId());
                answer.setClassname(item.getClassname());
                answer.setQuestionNumber(item.getQuestionNumber());
                answer.setQuestion(item.getQuestion());
                answer.setGrade(item.getGrade());
                answer.setIsMultity(item.getIsMultity());
                answer.setIsObjective(item.getIsObjective());
                answer.setHomeworkId(item.getHomeworkId());
                answer.setAnswer(JSON.toJSONString(item.getAnswer()));
                return answer;
            }).collect(Collectors.toList());
            for (HomeworkQuestionAnswer answer: answers) {
                logger.info(answer.getQuestion());
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
            HomeworkQuestionAnswer item = mapper.selectById(id);
            HomeworkQuestionAnswerWrap answer = new HomeworkQuestionAnswerWrap();
            answer.setGmtCreate(item.getGmtCreate());
            answer.setClassId(item.getClassId());
            answer.setClassname(item.getClassname());
            answer.setQuestionNumber(item.getQuestionNumber());
            answer.setQuestion(item.getQuestion());
            answer.setGrade(item.getGrade());
            answer.setIsMultity(item.getIsMultity());
            answer.setIsObjective(item.getIsObjective());
            answer.setHomeworkId(item.getHomeworkId());
            answer.setId(item.getId());
            JSON.parse(item.getAnswer());
            answer.setAnswer((Map<String, List>) JSON.parse(item.getAnswer()));
            return ResponseMessage.success(answer);
        }
        catch (Exception e) {
            logger.error("GetById Error", e);
            return ResponseMessage.fail();
        }
    }

    @GetMapping("/getBatchByHomeworkId")
    public ResponseMessage getBatchByHomeworkId(int id) {
        try {
            List<HomeworkQuestionAnswer> items = mapper.selectList(new QueryWrapper<HomeworkQuestionAnswer>()
                    .lambda()
                    .eq(HomeworkQuestionAnswer::getHomeworkId, id));
            List<HomeworkQuestionAnswerWrap> answers = items.stream().map((item) -> {
                HomeworkQuestionAnswerWrap answer = new HomeworkQuestionAnswerWrap();
                answer.setGmtCreate(item.getGmtCreate());
                answer.setClassId(item.getClassId());
                answer.setClassname(item.getClassname());
                answer.setQuestionNumber(item.getQuestionNumber());
                answer.setQuestion(item.getQuestion());
                answer.setGrade(item.getGrade());
                answer.setIsMultity(item.getIsMultity());
                answer.setIsObjective(item.getIsObjective());
                answer.setId(item.getId());
                answer.setHomeworkId(item.getHomeworkId());
                JSON.parse(item.getAnswer());
                answer.setAnswer((Map<String, List>) JSON.parse(item.getAnswer()));
                return answer;
            }).collect(Collectors.toList());

            return ResponseMessage.success(answers);
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
