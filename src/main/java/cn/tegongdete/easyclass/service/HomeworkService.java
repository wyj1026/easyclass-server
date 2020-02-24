package cn.tegongdete.easyclass.service;

import cn.tegongdete.easyclass.mapper.ClassHomeworkMapper;
import cn.tegongdete.easyclass.mapper.UserClassRoleMapper;
import cn.tegongdete.easyclass.model.Class;
import cn.tegongdete.easyclass.model.ClassHomework;
import cn.tegongdete.easyclass.model.UserClassRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeworkService {
    @Autowired
    private ClassHomeworkMapper classHomeworkMapper;

    public List<ClassHomework> getHomeworksByClassId(List<Integer> classId) {
        List<ClassHomework> classHomeworks = classHomeworkMapper.selectList(new QueryWrapper<ClassHomework>()
                .lambda().in(ClassHomework::getClassId, classId));
        return classHomeworks;
    }

}
