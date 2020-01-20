package cn.tegongdete.easyclass.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String nickname;
    private String password;
    private String phone;
    private String email;
    private String avatarUrl;

}
