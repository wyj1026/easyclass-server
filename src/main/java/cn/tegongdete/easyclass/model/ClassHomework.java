package cn.tegongdete.easyclass.model;

import lombok.Data;

@Data
public class ClassHomework {
    private Integer id;
    private Integer gmtCreate;
    private Integer classId;
    private String classname;
    private String homeworkTitle;
    private Integer questionNumber;
    private Integer totalGrade;
    private Integer gmtStartUpload;
    private Integer gmtStopUpload;
    private Boolean enableCommunicate;
    private Boolean enableAutoJudge;
    private Boolean enableJudgeByOthers;
}
