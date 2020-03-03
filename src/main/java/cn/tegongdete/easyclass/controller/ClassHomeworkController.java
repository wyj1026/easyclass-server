package cn.tegongdete.easyclass.controller;

import cn.tegongdete.easyclass.mapper.ClassHomeworkMapper;
import cn.tegongdete.easyclass.mapper.UserClassRoleMapper;
import cn.tegongdete.easyclass.model.*;
import cn.tegongdete.easyclass.service.HomeworkService;
import cn.tegongdete.easyclass.service.QuestionService;
import cn.tegongdete.easyclass.service.SummaryService;
import cn.tegongdete.easyclass.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "Homework Management")
@RestController
@RequestMapping("homework")
public class ClassHomeworkController {
    private static final Logger logger = LoggerFactory.getLogger(ClassHomeworkController.class);

    @Autowired
    private ClassHomeworkMapper classHomeworkMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private SummaryService summaryService;

    @Autowired
    private QuestionService questionService;

    @PostMapping("/new")
    public ResponseMessage signUp(ClassHomework item) {
        try {
            classHomeworkMapper.insert(item);
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
            classHomeworkMapper.updateById(item);
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
            ClassHomework u = classHomeworkMapper.selectById(id);
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
            classHomeworkMapper.deleteById(id);
            return ResponseMessage.success();
        }
        catch (Exception e) {
            logger.error("DeleteById Error", e);
            return ResponseMessage.fail();
        }
    }

    @GetMapping("/getToCommitByUserId")
    public ResponseMessage getToCommitByUserId(Integer id) {
        try {
            List<ClassHomework> toCommit = new ArrayList<>();
            List<Integer> classIds = userRoleService.getClassesByUserId(id, "student");
            if (classIds.isEmpty()) return ResponseMessage.success(toCommit);
            List<ClassHomework> homeworks = homeworkService.getHomeworksByClassId(classIds);
            if (homeworks.isEmpty()) return ResponseMessage.success(toCommit);
            List<Integer> answeredHomeworks = questionService.getQuestionAnswers(id).stream().map(questionStudentAnswer -> {
                return questionStudentAnswer.getHomeworkId();
            }).collect(Collectors.toList());
            for (ClassHomework homework: homeworks) {
                if (!answeredHomeworks.contains(homework.getId())) {
                    toCommit.add(homework);
                }
            }
            return ResponseMessage.success(toCommit);
        }
        catch (Exception e) {
            logger.error("GetBatchById Error", e);
            return ResponseMessage.fail();
        }
    }


    @GetMapping("/getNotJudgedByUserId")
    public ResponseMessage getNotJudgedByUserId(Integer id) {
        try {
            List<ClassHomework> toCommit = new ArrayList<>();
            List<Integer> classIds = userRoleService.getClassesByUserId(id, "student");
            if (classIds.isEmpty()) return ResponseMessage.success(toCommit);
            List<ClassHomework> homeworks = homeworkService.getHomeworksByClassId(classIds);
            if (homeworks.isEmpty()) return ResponseMessage.success(toCommit);
            Set<Integer> answeredHomeworks = questionService.getQuestionAnswers(id).stream().map(questionStudentAnswer -> {
                return questionStudentAnswer.getHomeworkId();
            }).collect(Collectors.toSet());
            List<QuestionStudentSummary> summaries = summaryService.getCommitted(id);
            for (ClassHomework homework: homeworks) {
                for (Integer answeredId: answeredHomeworks) {
                    if (homework.getId().equals(answeredId)) {
                        toCommit.add(homework);
                        for (QuestionStudentSummary summary: summaries) {
                            if (!toCommit.isEmpty() && summary.getHomeworkId().equals(toCommit.get(toCommit.size()-1).getId())) {
                                toCommit.remove(toCommit.size()-1);
                                break;
                            }
                        }
                    }
                }

            }
            return ResponseMessage.success(toCommit);
        }
        catch (Exception e) {
            logger.error("GetBatchById Error", e);
            return ResponseMessage.fail();
        }
    }

    @GetMapping("/getJudgedByUserId")
    public ResponseMessage getJudgedByUserId(Integer id) {
        try {
            List<ClassHomework> judged = new ArrayList<>();
            List<Integer> classIds = userRoleService.getClassesByUserId(id, "student");
            if (classIds.isEmpty()) return ResponseMessage.success(judged);
            List<ClassHomework> homeworks = homeworkService.getHomeworksByClassId(classIds);
            List<QuestionStudentSummary> summaries = summaryService.getCommitted(id);
            for (ClassHomework homework: homeworks) {
                for (QuestionStudentSummary summary: summaries) {
                    if (summary.getHomeworkId().equals(homework.getId())) {
                        judged.add(homework);
                        break;
                    }
                }
            }
            return ResponseMessage.success(judged);
        }
        catch (Exception e) {
            logger.error("GetBatchById Error", e);
            return ResponseMessage.fail();
        }
    }


    @GetMapping("/getPushedByTeacherUserId")
    public ResponseMessage getPushedByTeacherUserId(Integer id) {
        try {
            List<Integer> classIds = userRoleService.getClassesByUserId(id, "teacher");
            if (classIds.isEmpty()) return ResponseMessage.success(new ArrayList<>());
            List<ClassHomework> homeworks = homeworkService.getHomeworksByClassId(classIds);
            return ResponseMessage.success(homeworks);
        }
        catch (Exception e) {
            logger.error("GetBatchById Error", e);
            return ResponseMessage.fail();
        }
    }


    @GetMapping("/getToJudgeByTeacherUserId")
    public ResponseMessage getToJudgeByTeacherUserId(Integer id) {
        try {
            List<Integer> classIds = userRoleService.getClassesByUserId(id, "teacher");
            if (classIds.isEmpty()) return ResponseMessage.success(new ArrayList<>());
            List<ClassHomework> homeworks = homeworkService.getHomeworksByClassId(classIds);
            List<ClassHomework> result = new ArrayList<>();
            for (ClassHomework homework: homeworks) {
                Integer homeworkId = homework.getId();
                Integer questionNumber = homework.getQuestionNumber();
                List<QuestionStudentAnswer> answers = questionService.getQuestionAnswersByHomeworkId(homeworkId);
                List<QuestionStudentSummary> summaries = summaryService.getCommittedByHomeworkId(homeworkId);
                if (answers.size() != summaries.size() * homework.getQuestionNumber()) {
                    result.add(homework);
                }
            }
            return ResponseMessage.success(result);
        }
        catch (Exception e) {
            logger.error("GetBatchById Error", e);
            return ResponseMessage.fail();
        }
    }

    @GetMapping("/getJudgedByTeacherUserId")
    public ResponseMessage getJudgedByTeacherUserId(Integer id) {
        try {
            List<Integer> classIds = userRoleService.getClassesByUserId(id, "teacher");
            if (classIds.isEmpty()) return ResponseMessage.success(new ArrayList<>());
            List<ClassHomework> homeworks = homeworkService.getHomeworksByClassId(classIds);
            List<ClassHomework> result = new ArrayList<>();
            for (ClassHomework homework: homeworks) {
                Integer homeworkId = homework.getId();
                Integer questionNumber = homework.getQuestionNumber();
                List<QuestionStudentAnswer> answers = questionService.getQuestionAnswersByHomeworkId(homeworkId);
                List<QuestionStudentSummary> summaries = summaryService.getCommittedByHomeworkId(homeworkId);
                if (answers.size() != 0 && answers.size() == summaries.size() * homework.getQuestionNumber()) {
                    result.add(homework);
                }
            }
            return ResponseMessage.success(result);
        }
        catch (Exception e) {
            logger.error("GetBatchById Error", e);
            return ResponseMessage.fail();
        }
    }

    @GetMapping("/after")
    public ResponseMessage getHomeworkAfterDate(Integer id, Long timestamp) {
        try {
            List<ClassHomework> toCommit = new ArrayList<>();
            List<Integer> classIds = userRoleService.getClassesByUserId(id, "student");
            if (classIds.isEmpty()) return ResponseMessage.success(toCommit);
            List<ClassHomework> homeworks = homeworkService.getHomeworksByClassId(classIds);

            for (ClassHomework classHomework: homeworks) {
                if (classHomework.getGmtCreate() > timestamp) {
                    toCommit.add(classHomework);
                }
            }
            return ResponseMessage.success(toCommit);
        }
        catch (Exception e) {
            logger.error("GetHomeworkAfterDate Error", e);
            return ResponseMessage.fail();
        }
    }

    @GetMapping("/near")
    public ResponseMessage getHomeworkNearDate(Integer id, Long timestamp) {
        try {
            List<ClassHomework> toCommit = new ArrayList<>();
            List<Integer> classIds = userRoleService.getClassesByUserId(id, "student");
            if (classIds.isEmpty()) return ResponseMessage.success(toCommit);
            List<ClassHomework> homeworks = homeworkService.getHomeworksByClassId(classIds);

            for (ClassHomework classHomework: homeworks) {
                if (classHomework.getGmtStopUpload() < timestamp && classHomework.getGmtStopUpload() > System.currentTimeMillis()) {
                    toCommit.add(classHomework);
                }
            }
            return ResponseMessage.success(toCommit);
        }
        catch (Exception e) {
            logger.error("GetHomeworkNearDate Error", e);
            return ResponseMessage.fail();
        }
    }


    @GetMapping("/stop")
    public ResponseMessage getHomeworkStoppedUpload(Integer id, Long timestamp) {
        try {
            List<ClassHomework> toCommit = new ArrayList<>();
            List<Integer> classIds = userRoleService.getClassesByUserId(id, "teacher");
            if (classIds.isEmpty()) return ResponseMessage.success(toCommit);
            List<ClassHomework> homeworks = homeworkService.getHomeworksByClassId(classIds);

            for (ClassHomework classHomework: homeworks) {
                if (classHomework.getGmtStopUpload() < timestamp) {
                    toCommit.add(classHomework);
                }
            }
            return ResponseMessage.success(toCommit);
        }
        catch (Exception e) {
            logger.error("GetHomeworkStoppedUpload Error", e);
            return ResponseMessage.fail();
        }
    }

}
