package cn.tegongdete.easyclass.service;

import cn.tegongdete.easyclass.mapper.QuestionStudentAnswerMapper;
import cn.tegongdete.easyclass.mapper.QuestionStudentSummaryMapper;
import cn.tegongdete.easyclass.model.QuestionStudentAnswer;
import cn.tegongdete.easyclass.model.QuestionStudentSummary;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionStudentAnswerMapper questionStudentAnswerMapper;

    public List<QuestionStudentAnswer> getQuestionAnswers(Integer userId, Integer homeworkId) {
        List<QuestionStudentAnswer> items = questionStudentAnswerMapper.selectList(new QueryWrapper<QuestionStudentAnswer>()
                .lambda().eq(QuestionStudentAnswer::getUserId, userId).eq(QuestionStudentAnswer::getHomeworkId, homeworkId));
        return items;
    }

    public List<QuestionStudentAnswer> getQuestionAnswers(Integer userId) {
        List<QuestionStudentAnswer> items = questionStudentAnswerMapper.selectList(new QueryWrapper<QuestionStudentAnswer>()
                .lambda().eq(QuestionStudentAnswer::getUserId, userId));
        return items;
    }

    public List<QuestionStudentAnswer> getQuestionAnswersByHomeworkId(Integer homeworkId) {
        List<QuestionStudentAnswer> items = questionStudentAnswerMapper.selectList(new QueryWrapper<QuestionStudentAnswer>()
                .lambda().eq(QuestionStudentAnswer::getHomeworkId, homeworkId));
        return items;
    }
}
