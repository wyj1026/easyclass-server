package cn.tegongdete.easyclass.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HomeworkQuestionAnswerWrap {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Long gmtCreate;
    private Integer classId;
    private Integer homeworkId;
    private String classname;
    private Integer questionNumber;
    private String question;
    private Map<String, List> answer;
    private Integer grade;
    private Boolean isObjective;
    private Boolean isMultity;
}
