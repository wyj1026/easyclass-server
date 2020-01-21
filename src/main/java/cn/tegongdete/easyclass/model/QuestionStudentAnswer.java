package cn.tegongdete.easyclass.model;

import lombok.Data;

@Data
public class QuestionStudentAnswer {
    private Integer id;
    private Integer classId;
    private String classname;
    private Integer homeworkId;
    private Integer userId;
    private String username;
    private Integer homeworkQuestionId;
    private String studentQuestionAnswer;
    private String grade;
    private Integer gmtUpload;
    private Integer gmtJudge;
    private String comment;
}
