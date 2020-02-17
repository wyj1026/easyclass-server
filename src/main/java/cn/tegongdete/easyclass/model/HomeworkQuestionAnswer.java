package cn.tegongdete.easyclass.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class HomeworkQuestionAnswer {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer gmtCreate;
    private Integer classId;
    private String classname;
    private Integer questionNumber;
    private Integer question;
    private String answer;
    private Integer grade;
    private Boolean isObjective;
}
