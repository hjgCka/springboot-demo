package com.hjg.spring.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户实体")
public class User {

    @ApiModelProperty("用户名称")
    private String name;

    @ApiModelProperty("用户年龄")
    private int age;

    @ApiModelProperty("用户手机号")
    private String mobile;
}
