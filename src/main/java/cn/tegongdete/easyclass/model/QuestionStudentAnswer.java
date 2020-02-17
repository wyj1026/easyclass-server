package cn.tegongdete.easyclass.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class QuestionStudentAnswer {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer classId;
    private String classname;
    private Integer homeworkId;
    private Integer userId;
    private String username;
    private Integer homeworkQuestionId;
    private String studentQuestionAnswer;
    private String grade;
    private Long gmtUpload;
    private Long gmtJudge;
    private String comment;
}
