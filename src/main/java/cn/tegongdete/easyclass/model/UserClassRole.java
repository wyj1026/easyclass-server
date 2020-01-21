package cn.tegongdete.easyclass.model;

import lombok.Data;

@Data
public class UserClassRole {
    private Integer id;
    private Integer userId;
    private String username;
    private String school;
    private Integer classId;
    private String className;
    private String role;

}
