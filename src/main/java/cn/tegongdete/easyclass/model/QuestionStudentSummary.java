package cn.tegongdete.easyclass.model;

import lombok.Data;

@Data
public class QuestionStudentSummary {
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
