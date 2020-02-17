package cn.tegongdete.easyclass.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Class {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String classname;
    private Integer gmtStart;
    private String classDate;
    private String avatarUrl;
}
