package cn.tegongdete.easyclass.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class QuestionStudentSummary {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer classId;
    private String classname;
    private Integer homeworkId;
    private Integer userId;
    private String username;
    private Integer objectiveGrage;
    private Integer nonObjectiveGrage;
    private String comment;
}
