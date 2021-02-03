package com.hjg.mybatis.plus.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/28
 */
@Data
@TableName("t_user")
public class User {
    @TableId("id_")
    private Integer id;

    @TableField("name_")
    private String name;

    @TableField("age_")
    private Integer age;

    @TableField("email_")
    private String email;

    @TableField("birthday_")
    private Date birthDay;

    @Version
    @TableField("version_")
    private Integer version;
}
