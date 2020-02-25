package cn.tegongdete.easyclass.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StudentQuestionAnswerWrap {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer classId;
    private String classname;
    private Integer homeworkId;
    private Integer userId;
    private String username;
    private Integer homeworkQuestionId;
    private List studentQuestionAnswer;
    private Integer grade;
    private Long gmtUpload;
    private Long gmtJudge;
    private String comment;
}
