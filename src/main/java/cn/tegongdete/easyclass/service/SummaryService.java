package cn.tegongdete.easyclass.service;

import cn.tegongdete.easyclass.mapper.QuestionStudentSummaryMapper;
import cn.tegongdete.easyclass.mapper.UserClassRoleMapper;
import cn.tegongdete.easyclass.model.QuestionStudentAnswer;
import cn.tegongdete.easyclass.model.QuestionStudentSummary;
import cn.tegongdete.easyclass.model.UserClassRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SummaryService {
    @Autowired
    private QuestionStudentSummaryMapper questionStudentSummaryMapper;

    public List<QuestionStudentSummary> getCommitted(Integer userId) {
        List<QuestionStudentSummary> items = questionStudentSummaryMapper.selectList(new QueryWrapper<QuestionStudentSummary>()
                .lambda().eq(QuestionStudentSummary::getUserId, userId));
        return items;
    }

    public List<QuestionStudentSummary> getCommittedByHomeworkId(Integer homeworkId) {
        List<QuestionStudentSummary> items = questionStudentSummaryMapper.selectList(new QueryWrapper<QuestionStudentSummary>()
                .lambda().eq(QuestionStudentSummary::getHomeworkId, homeworkId));
        return items;
    }

    public void saveSummary(List<QuestionStudentAnswer> answers) {
        if (answers.isEmpty()) return;
        QuestionStudentAnswer aanswer = answers.get(0);
        QuestionStudentSummary summary = new QuestionStudentSummary();
        summary.setClassId(aanswer.getClassId());
        summary.setClassname(aanswer.getClassname());
//        summary.setComment();
        summary.setHomeworkId(aanswer.getHomeworkId());
        summary.setNonObjectiveGrage(0);
        summary.setObjectiveGrage(0);
        summary.setUserId(aanswer.getUserId());
        summary.setUsername(aanswer.getUsername());
        questionStudentSummaryMapper.insert(summary);
    }
}
