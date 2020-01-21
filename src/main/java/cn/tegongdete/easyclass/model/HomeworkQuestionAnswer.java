package cn.tegongdete.easyclass.model;

import lombok.Data;

@Data
public class HomeworkQuestionAnswer {
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
